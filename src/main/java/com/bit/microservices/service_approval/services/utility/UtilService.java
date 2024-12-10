package com.bit.microservices.service_approval.services.utility;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.model.bit.va.ConditionQueryEnum;
import com.bit.microservices.model.bit.va.ConjunctionConditionEnum;
import com.bit.microservices.model.bit.va.WhereConditionDTO;
import com.bit.microservices.model.bit.va.configuration.FieldDetailDTO;
import com.bit.microservices.model.bit.va.configuration.FilterableField;
import com.bit.microservices.model.bit.va.configuration.SortDto;
import com.bit.microservices.model.bit.va.request.RequestPagingDTO;
import com.bit.microservices.service_approval.configuration.ParserFactory;
import com.bit.microservices.service_approval.exceptions.InternalServerErrorException;
import com.bit.microservices.service_approval.exceptions.NotAcceptableException;
import com.bit.microservices.service_approval.model.GeneratedDslDto;
import com.bit.microservices.service_approval.utils.annotations.RelatedEntities;
import com.bit.microservices.service_approval.utils.annotations.RelatedEntity;
import com.bit.microservices.utils.FieldInfo;
import com.bit.microservices.utils.PageImplCustom;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@NoArgsConstructor
public class UtilService {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Autowired
    private EntityManager em;

    @Autowired
    private ParserFactory parserFactory;

    private String camelToUnderScore(String value){
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
        return value.replaceAll(regex, replacement).toLowerCase();
    }

    public Pageable pageableUtils(RequestPagingDTO request) {
        int page = 1;
        int size = 20;


        if (request.getPage() > 0) {
            page = request.getPage();
        }

        if (request.getSize() > 0) {
            size = request.getSize();
        }


        Pageable pageable = PageRequest.of(page, size);
        if (request.getSortBy() != null && !request.getSortBy().isBlank() && !request.getSortBy().isEmpty()) {
            Sort.Order order = new Sort.Order(request.getSortDirection(), request.getSortBy());

            pageable = PageRequest.of(page, size, Sort.by(order));
        }


        return pageable;

    }

    public BooleanExpression nestedExpression(Predicate predicate) {

        return Expressions.predicate(Ops.WRAPPED, new Expression[] {predicate});

    }

//    public BooleanExpression generateExpression(FieldDetailDTO field,WhereConditionDTO condition) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Object path = field.getExpression();
//        String fieldName = field.getKey();
////    this.em.createNativeQuery("").getResultList().get
//        try{
//            if(!Objects.isNull(ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryClass)){
//                String queryNative = ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryNative;
//                queryNative = queryNative.replaceFirst("$field",fieldName);
//                Method method = path.getClass().getMethod(ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryDsl,ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryClass);
//                Object value =parserFactory.getParsedValue(condition.getValue(),field.getFieldType());
//                queryNative = queryNative.replaceFirst("$value",value.toString());
//                return (BooleanExpression) method.invoke(path,value);
//            }
//            else {
//
//                Method method =path.getClass().getMethod(ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryDsl,null);
//
//                return (BooleanExpression) method.invoke(path,null);
//            }
//
//
//        }catch (Exception e){
//
//            return null;
//        }
//    }

    public String generateExpression(FieldDetailDTO field, WhereConditionDTO condition) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String fieldName = field.getField();
        try{
            Class queryClazz = ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryClass;
            if(!Objects.isNull(queryClazz)){
                String queryNative = ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryNative;
                queryNative = queryNative.replaceAll("\\$field",fieldName);
                Object value =null;
                String valueTyped = "";
                if(queryClazz.equals(Collection.class)){
                    List<Object> listTest = (List<Object>) condition.getValue();

                    value = "('"+listTest.stream().map(String::valueOf).collect(Collectors.joining("','"))+"')";

                    valueTyped =value.toString();
                }else{
                    value =parserFactory.getParsedValue(condition.getValue(),field.getFieldType(),field.getEntityType());
                    valueTyped ="'"+value.toString()+"'";
                }
                queryNative = queryNative.replaceAll("\\$value",valueTyped);

                return queryNative;
            }
            else {
                String queryNative = ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).queryNative;
                queryNative = queryNative.replaceAll("\\$field",fieldName);

                return queryNative;
            }


        }catch (Exception e){
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());

            return null;
        }
    }


    public GeneratedDslDto generateQueryDsl(Class<?> clazz,GeneratedDslDto dslDto) throws InvocationTargetException, IllegalAccessException{
        dslDto = generateQueryDsl(clazz,dslDto,"");
        Set<String> groupsParent = new HashSet<>();
        dslDto.getGroups().stream().forEach((fieldName)->{
            groupsParent.add(fieldName);
        });
        dslDto.setGroupsOnParentClass(groupsParent);
        Annotation annotationClass =clazz.getAnnotation(RelatedEntities.class);

        RelatedEntities relatedEntities = (RelatedEntities) annotationClass;
        if(!Objects.isNull(relatedEntities)){
            for(int i =0; i<relatedEntities.value().length;i++){
                RelatedEntity relatedEntity = relatedEntities.value()[i];

                dslDto = generateQueryDsl(relatedEntity.dtoClass(),dslDto,relatedEntity.prefix());

            }
        }

        return dslDto;

    }

    private GeneratedDslDto generateQueryDsl(Class<?> clazz, GeneratedDslDto dslDto, String prefix) throws InvocationTargetException, IllegalAccessException{
        HashMap<String,FieldDetailDTO> mapExp = dslDto.getFields();
        HashMap<String,FieldDetailDTO> mapHavingExp = dslDto.getHavingFields();
        Field[] fields = clazz.getDeclaredFields();
        Set<FilterableField> filterableFields = dslDto.getFilterableFields();
        Set<String> groups = dslDto.getGroups();
        Set<String> selects = dslDto.getSelects();
        for(Field field:fields){

            Annotation fieldAnnotation =field.getAnnotation(FieldInfo.class);

            FieldInfo fieldInfo = (FieldInfo) fieldAnnotation;
            if(!Objects.isNull(fieldInfo)){

                String entityName = fieldInfo.entityName();
                String fieldName = fieldInfo.fieldName();
                String exampleValue = fieldInfo.example();
                String entityField= fieldInfo.entityField().isEmpty()?field.getName(): fieldInfo.entityField();
                String entityType= fieldInfo.entityType();
                String getEntityType = entityType.substring(0,1).toUpperCase()+entityType.substring(1).toLowerCase();
                String groupFunc = fieldInfo.groupFunc();
                boolean isHavingFilter = fieldInfo.havingFilter();
                String alias = fieldInfo.alias();
                boolean filterAble = fieldInfo.filterable();
                boolean selectAble = fieldInfo.selectable();

                String fieldValue = fieldName.isEmpty()?null:fieldName;
                if(filterAble)
                    filterableFields.add(new FilterableField(field.getName(),alias.isEmpty()?field.getName():alias,entityType,exampleValue));


                if(fieldValue!=null && selectAble){

                    if(!groupFunc.isEmpty()) {
                        try {
                            fieldValue = groupFunc+"("+fieldName+")";
                            FieldDetailDTO fieldDto = new FieldDetailDTO(field.getType(),fieldValue,entityName+"."+fieldValue,fieldInfo.entityType());
                            mapHavingExp.put(field.getName(),fieldDto);
                        } catch (Exception e) {
                            ExceptionPrinter print = new ExceptionPrinter(e);
                            log.error(print.getMessage());
                            throw new RuntimeException(e);
                        }
                    }else if(isHavingFilter && groupFunc.isEmpty()){
                        groups.add(entityName+"."+fieldValue);
                        FieldDetailDTO fieldDto = new FieldDetailDTO(field.getType(),fieldValue,entityName+"."+fieldValue,fieldInfo.entityType());
                        mapHavingExp.put(prefix+field.getName(),fieldDto);
                    }
                    else{
                        groups.add(entityName+"."+fieldValue);
                        FieldDetailDTO fieldDto = new FieldDetailDTO(field.getType(),fieldValue,entityName+"."+fieldValue,fieldInfo.entityType());
                        mapExp.put(prefix+field.getName(),fieldDto);
                    }


                    selects.add(entityName+"."+fieldValue);

                }

            }


        }
        dslDto.setFields(mapExp);
        dslDto.setHavingFields(mapHavingExp);
        dslDto.setFilterableFields(filterableFields);
        dslDto.setGroups(groups);
        dslDto.setSelects(selects);
        return dslDto;
    }

    public <T> List<T> transForm(Class<T> clazz, GeneratedDslDto dslDto, List<Tuple> results) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Field[] fields = clazz.getDeclaredFields();
        List<T> arrayList = new ArrayList<>();

        for (Tuple result : results) {

            T object =clazz.getDeclaredConstructor().newInstance();
            for (Field field : fields) {
                String fieldName = field.getName();

                String methodName= "set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                FieldDetailDTO detailDTO =dslDto.getFields().get(fieldName);
                if(Objects.isNull(detailDTO))
                    detailDTO=dslDto.getHavingFields().get(fieldName);


                if(!Objects.isNull(detailDTO)){
                    try{

                        Object data =result.get(detailDTO.getKey());


                        if(!Objects.isNull(data)){

                            Method method =clazz.getMethod(methodName,field.getType());

                            method.invoke(object,mapTo(data,field.getType()));
                        }
                    }catch (Exception e){
                        ExceptionPrinter print = new ExceptionPrinter(e);
                        log.error(print.getMessage());
                    }

                }

                log.info(object.toString());
            }
            arrayList.add(object);

        }
        return arrayList;
    }
    private LocalDateTime map(OffsetDateTime dateTime){
        if (dateTime != null){
            return dateTime.toLocalDateTime();
        } else {
            return null;
        }
    }

    private OffsetDateTime map(LocalDateTime dateTime){

        if (dateTime != null){
            return dateTime.atOffset(ZoneOffset.UTC);
        } else {
            return null;
        }
    }
    private Object map(Date dateTime, Class<?> clazzTo){
        if (dateTime == null) {
            return null;
        }

        if(clazzTo.equals(OffsetDateTime.class))
            return dateTime.toInstant().atOffset(ZoneOffset.UTC);
        else return dateTime.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
    }

//    private Object map(Timestamp dateTime, Class<?> clazzTo){
//        if (dateTime == null) {
//            return null;
//        }
//        if(clazzTo.equals(OffsetDateTime.class))
//            return OffsetDateTime.from(dateTime.toInstant());
//        else return LocalDateTime.from(dateTime.toInstant());
//
//    }



    private Object mapTo(Object data,Class<?> clazzTo){
//        if

        if(clazzTo.equals(LocalDateTime.class) && data instanceof OffsetDateTime){
            OffsetDateTime time= (OffsetDateTime) data;
            return map(time);
        }else if(clazzTo.equals(OffsetDateTime.class) && data instanceof LocalDateTime){
            LocalDateTime time = (LocalDateTime) data;
            return map(time);
        }
        else if (data instanceof Date){
            Date time = (Date) data;
            Object dataTemp = map(time,clazzTo);


            log.info("{}transform data", LINE_SEPARATOR);

            return dataTemp;


        }
        else{
            return data;
        }
    }


    public GeneratedDslDto generateQueryMetaData(Class<?> clazz){
        try {
            HashMap<String,FieldDetailDTO> mapExp = new HashMap<>();
            HashMap<String,FieldDetailDTO> mapHavingExp = new HashMap<>();
            HashMap<String,Class> mapParsedValue = new HashMap<>();
            Field[] fields = clazz.getDeclaredFields();
            Set<FilterableField> filterableFields = new HashSet<>();
            Set<String> groups = new HashSet<>();
            Set<String> selects = new HashSet<>();
            GeneratedDslDto generatedDslDto = new GeneratedDslDto();

            generatedDslDto.setFields(mapExp);
            generatedDslDto.setHavingFields(mapHavingExp);
            generatedDslDto.setFilterableFields(filterableFields);
            generatedDslDto.setGroups(groups);
            generatedDslDto.setSelects(selects);
            generatedDslDto.setClassReflect(clazz);
            GeneratedDslDto dslDto  =generateQueryDsl(clazz,generatedDslDto);
            return dslDto;
        } catch (InvocationTargetException e) {
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());
            throw new InternalServerErrorException(e.getMessage(),null);
        } catch (IllegalAccessException e) {
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());
            throw new InternalServerErrorException(e.getMessage(),null);
        }
        catch (Exception e){
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());
            throw new InternalServerErrorException(e.getMessage(),null);
        }

    }

    public void setConditionQuery(GeneratedDslDto dslDto,RequestPagingDTO request){

        if(!request.getSortBy().isEmpty()){
            FieldDetailDTO fieldDtoTemp = dslDto.getFields().get(request.getSortBy());
            FieldDetailDTO fieldDto = !Objects.isNull(fieldDtoTemp)?fieldDtoTemp:dslDto.getHavingFields().get(request.getSortBy());

            if(!Objects.isNull(fieldDto)){
                FieldDetailDTO sortBy = Objects.isNull(dslDto.getFields().get(request.getSortBy()))?dslDto.getHavingFields().get(request.getSortBy()):dslDto.getFields().get(request.getSortBy());
                dslDto.setOrder(new SortDto(request.getSortDirection().name(),sortBy.getField()));

            }

        }


        if(!Objects.isNull(request.getWhere())){
            dslDto.setWhere(generatedBooleanExpression(request.getWhere(),dslDto.getFields()));

            dslDto.setHaving(generatedBooleanExpression(request.getWhere(),dslDto.getHavingFields()));
        }
    };

    public <T> Map<String,T> findMap(String query,GeneratedDslDto dslDto,Pageable pageable){
        if(!Objects.isNull(dslDto.getOrder())){
            query = query.concat(" order by "+dslDto.getOrder().getFieldName()+" "+dslDto.getOrder().getSortDirection());
        }
        String groupFieldParentStr = dslDto.getGroups().stream().collect(Collectors.joining(","));
        Query countRow = this.em.createNativeQuery("" +
                "select count(*) from ("+query+") group by "+ groupFieldParentStr
        );

        int nextPage =(pageable.getPageNumber())*pageable.getPageSize();
        int startPage = (pageable.getPageNumber()-1)*pageable.getPageSize();

        String queryLimitStr ="select * from ("+query+") group by "+groupFieldParentStr +" limit "+pageable.getPageSize()+" offset "+startPage;

        Query queryFull = this.em.createNativeQuery(query,Tuple.class);
        Query queryLimit = this.em.createNativeQuery(queryLimitStr,Tuple.class);

        List<Tuple> data = queryFull.getResultList();
        List<Tuple> dataParent = queryLimit.getResultList();

        Object totalRow = countRow.getSingleResult();



        try {
            List<T> dataResult = transForm(dslDto.getClassReflect(), dslDto,data);
            return null;
//            return new PageImplCustom<>(dataResult,pageable,Long.parseLong(totalRow.toString()),dslDto.getFilterableFields().stream().toList());
        }catch (Exception e){
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());

            throw new NotAcceptableException("Failed while get result list",null);
        }
    }

    public <T> Page<T> find(String query, GeneratedDslDto dslDto, Pageable pageable){

        if(Objects.isNull(dslDto.getClassReflect().getAnnotation(RelatedEntities.class))){
            if(!Objects.isNull(dslDto.getOrder())){
                query = query.concat(" order by "+dslDto.getOrder().getFieldName()+" "+dslDto.getOrder().getSortDirection());
            }
            Query countRow = this.em.createNativeQuery("" +
                    "select count(*) from ("+query+") as sub"
            );
            int nextPage =(pageable.getPageNumber())*pageable.getPageSize();
            int startPage = (pageable.getPageNumber()-1)*pageable.getPageSize();
            query = query.concat(" limit "+pageable.getPageSize()).concat(" offset "+startPage);
            Query queryNative = this.em.createNativeQuery(query,Tuple.class);

            List<Tuple> data = queryNative.getResultList();

            Object totalRow = countRow.getSingleResult();



            try {
                List<T> dataResult = transForm(dslDto.getClassReflect(), dslDto,data);

                return new PageImplCustom<>(dataResult,pageable,Long.parseLong(totalRow.toString()),dslDto.getFilterableFields().stream().toList());
            }catch (Exception e){
                ExceptionPrinter print = new ExceptionPrinter(e);
                log.error(print.getMessage());

                throw new NotAcceptableException("Failed while get result list",null);
            }
        }else{
            throw new NotAcceptableException("Failed Method Find Incompatabile to Class Type",null);
        }

    }

    //    public <T> Page<T> find(CustomJPAQuery query, GeneratedDslDto dslDto, Pageable pageable){
//
//
//        System.out.println("query jalan");
//        System.out.println(query.getMetadata());
//        System.out.println(query);
//        String str = query.createQuery().unwrap(Query.class).getQueryString();
//        System.out.println("query string");
//        str = str.replaceAll("with","on");
//
//        System.out.println(str);
//        this.em.createNativeQuery("""
//                select billing.billCategory, CASE WHEN (billing.billAmount+billing.adminFee+billing.lateCharge)-(sum(payment.paidAmount)+billing.maxLessAmount)<=0 then 'PAID'
//                WHEN sum(payment.paidAmount)>0 THEN 'PARTIAL'
//                ELSE 'UNPAID' END
//                , billing.adminFee, billing.lateCharge, billing.billAmount, CASE WHEN (sum(payment.paidAmount) is null) THEN 0 ELSE sum(payment.paidAmount) END, billing.maxLessAmount, billing.customerCode, billing.billerId, billing.id, billing.maxMoreAmount, billing.locationId, billing.createdDate, billing.billDueDate, billing.customerName, CASE WHEN (sum(payment.paidAmount) is null) THEN (billing.billAmount+billing.adminFee+billing.lateCharge) ELSE (billing.billAmount+billing.adminFee+billing.lateCharge)-sum(payment.paidAmount) END, billing.branchId, billing.minPayment, billing.billingNumber, billing.locationUrl, case when Date(billing.billDueDate)<Date(CURRENT_DATE) then 'OVERDUE' when Date(billing.billDueDate)>Date('2024-02-26') then 'MORE THAN 3 DAYS' else 'LESS THAN 3 DAYS' end , string_agg(payment.statusBca,',')
//                , billing.customerHp, billing.virtualAccountNo, billing.customerEmail, billing.expiredDate
//                from Billing billing
//                  left join Payment payment on payment.billingNumber = billing.billingNumber and (payment.isActive = true and payment.statusBca <> 'REJECTEED')
//
//                group by billing.createdDate, billing.billCategory, billing.billDueDate, billing.customerName, billing.adminFee, billing.lateCharge, billing.billAmount, billing.maxLessAmount, billing.customerCode, billing.branchId, billing.minPayment, billing.billingNumber, billing.locationUrl, billing.billerId, billing.id, billing.customerHp, billing.virtualAccountNo, billing.customerEmail, billing.maxMoreAmount, billing.locationId, billing.expiredDate
//
//                """).getResultList();
//
//
//        System.out.println(query.createQuery().toString());
////        this.em.createNativeQuery(query.createQuery().get)
//        System.out.println("query jalan 3");
//        List<Tuple> dataAll = query.fetch();
//        int nextPage =(pageable.getPageNumber())*pageable.getPageSize();
//        int startPage = (pageable.getPageNumber()-1)*pageable.getPageSize();
//        int total = nextPage>dataAll.size()?dataAll.size():nextPage;
//
////       int total = (pageable.getPageNumber()+1)*pageable.getPageSize()
//        List<Tuple> data = dataAll.subList(startPage,total);
//
//        try {
//            List<T> dataResult = transForm(dslDto.getClassReflect(), dslDto,data);
//
//            return new PageImplCustom<>(dataResult,pageable,dataAll.size(),dslDto.getFilterableFields().stream().toList());
//        }catch (Exception e){
//            throw new NotAcceptableException("Failed while get result list");
//        }
//    }
//
//    public <T> List<T> find(CustomJPAQuery query, GeneratedDslDto dslDto){
//        List<Tuple> data = query.fetch();
//        try {
//            List<T> dataResult = transForm(dslDto.getClassReflect(), dslDto,data);
//            return dataResult;
//        }catch (Exception e){
//            throw new NotAcceptableException("Failed while get result list");
//        }
//
//    }
    public String generatedBooleanExpression(List<WhereConditionDTO> where, HashMap<String, FieldDetailDTO> fields){


        String finalExp = null;

        for(WhereConditionDTO condition: where){
            try {

                FieldDetailDTO path = fields.get(condition.getField());

                if(!Objects.isNull(path)) {


                    if(Objects.isNull(finalExp)){
                        finalExp = generateExpression(path,condition);

                    }

                    else{
                        String conjunction = ConjunctionConditionEnum.valueOf(condition.getConjunction().toUpperCase()).queryDsl;

                        String anotherExpresion = generateExpression(path,condition);
                        if(!Objects.isNull(anotherExpresion))
                            finalExp =finalExp + " "+conjunction+" "+anotherExpresion;



                    }

                    if(!Objects.isNull(condition.getInner()) && !condition.getInner().getWhere().isEmpty()){
                        String conjunction = ConjunctionConditionEnum.valueOf(condition.getConjunction().toUpperCase()).queryDsl;
                        String innerExp ="("+generatedBooleanExpression(condition.getInner().getWhere(),fields)+")";
                        finalExp =finalExp + " "+conjunction+" "+innerExp;
                    }
                }else if(!Objects.isNull(condition.getInner())){

                    if(Objects.isNull(finalExp)){
                        finalExp="("+generatedBooleanExpression(condition.getInner().getWhere(),fields)+")";
                    }
                    else{
                        String conjunction = ConjunctionConditionEnum.valueOf(condition.getInner().getConjunction().toUpperCase()).queryDsl;
                        String innerExp ="("+generatedBooleanExpression(condition.getInner().getWhere(),fields)+")";
                        finalExp =finalExp + " "+conjunction+" "+innerExp;
                    }

                }


//                Field field = clazz.getDeclaredField(condition.getField());
//                Annotation fieldAnnotation =field.getAnnotation(FieldInfo.class);
//                FieldInfo fieldInfo = (FieldInfo) fieldAnnotation;
//                String entityName = fieldInfo.entityName();
                //get StringPath,NumberPath,DatePath
//                PathBuilder pathBuilder =  new PathBuilder(clazz,entityName);

//                Method method = Arrays.stream(methods).filter(methodItem->methodItem.getName().equals(ConditionQueryEnum.valueOf(condition.getCondition().toUpperCase()).pathDsl)).findFirst().orElse(null);

            }
            catch (Exception e) {

                ExceptionPrinter print = new ExceptionPrinter(e);
                log.error("{}error query {}{}", LINE_SEPARATOR, print.getMessage());

                throw new NotAcceptableException("error Query in field "+condition.getField() +" with value : "+condition.getValue(),null);
            }
        }
        if(Objects.isNull(finalExp))
            finalExp ="true = true";

        return finalExp;

    }



    public BooleanExpression generateWhereCondition(List<WhereConditionDTO> param, EntityPathBase clazz) throws Exception{
        Field[] fields = clazz.getClass().getDeclaredFields();
        BooleanExpression finalExpression =null;

        for(WhereConditionDTO condition: param){
            Field field = Arrays.stream(fields).filter(x -> x.getName().equalsIgnoreCase(condition.getField())).findFirst().orElse(null);

            if (field != null){
                try {
                    if(Objects.isNull(finalExpression))
                        finalExpression= generateExpressionReflect(field,condition,clazz);
                    else{

                        Method class2 = finalExpression.getClass().getMethod(ConjunctionConditionEnum.valueOf(condition.getConjunction().toUpperCase()).queryDsl, Predicate.class);
//
                        BooleanExpression anotherExpresion =generateExpressionReflect(field,condition,clazz);

                        finalExpression =(BooleanExpression) class2.invoke(finalExpression,anotherExpresion);

                    }


                } catch (Exception e) {
                    ExceptionPrinter print = new ExceptionPrinter(e);
                    log.error(print.getMessage());
                    throw new Exception("error Query in field "+condition.getField() +" with value : "+condition.getValue());
                }
            }

        }
        if(Objects.isNull(finalExpression))
            finalExpression =Expressions.asBoolean(true).isTrue();

        return finalExpression;
    }

    private static BooleanExpression generateExpressionReflect(Field field, WhereConditionDTO param,EntityPathBase clazz) throws Exception{

        try {
            Method class2 = null;
            BooleanExpression obj= null;
            if(!Objects.isNull(ConditionQueryEnum.valueOf(param.getCondition().toUpperCase()).queryClass)){
                class2 =field.getType().getMethod(ConditionQueryEnum.valueOf(param.getCondition().toUpperCase()).queryDsl,ConditionQueryEnum.valueOf(param.getCondition().toUpperCase()).queryClass);
                obj=(BooleanExpression) class2.invoke(field.get(clazz),param.getValue());
            }
            else {
                class2 =field.getType().getMethod(ConditionQueryEnum.valueOf(param.getCondition().toUpperCase()).queryDsl,null);

                obj=(BooleanExpression) class2.invoke(field.get(clazz),null);
            }


            return obj;
        } catch (IllegalAccessException e) {
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());

            throw new Exception(e.getMessage());
        } catch (NoSuchMethodException e) {
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());

            throw new Exception(e.getMessage());
        } catch (InvocationTargetException e) {
            ExceptionPrinter print = new ExceptionPrinter(e);
            log.error(print.getMessage());

            throw new Exception(e.getMessage());
        }

    }

}


