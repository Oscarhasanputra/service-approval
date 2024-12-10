package com.bit.microservices.service_approval.utils.pageable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ChunkData<T> implements Serializable,SliceData<T>
{
    private static final long serialVersionUID = 867755909294344406L;
    private final List<T> result = new ArrayList();
    private final Pageable pageable;
    private final long total;

    public ChunkData(List<T> content, Pageable pageable, long total) {
        Assert.notNull(content, "Content must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        this.result.addAll(content);
        this.pageable = pageable;
        this.total = total;
    }

    public int getNumber() {
        return this.pageable.isPaged() ? this.pageable.getPageNumber() : 0;
    }

    public int getSize() {
        return this.pageable.isPaged() ? this.pageable.getPageSize() : this.result.size();
    }

    public int getNumberOfElements() {
        return (int)this.total;
    }

    public boolean hasPrevious() {
        return this.getNumber() > 0;
    }

    public boolean isFirst() {
        return !this.hasPrevious();
    }

    public boolean hasNext() {
        return false;
    }

    public boolean isLast() {
        return !this.hasNext();
    }

    public Pageable nextPageable() {
        return this.hasNext() ? this.pageable.next() : Pageable.unpaged();
    }

    public Pageable previousPageable() {
        return this.hasPrevious() ? this.pageable.previousOrFirst() : Pageable.unpaged();
    }

    public boolean hasResult() {
        return !this.result.isEmpty();
    }

    public List<T> getResult() {
        return Collections.unmodifiableList(this.result);
    }

    public Pageable getPageable() {
        return this.pageable;
    }

    public Sort getSort() {
        return this.pageable.getSort();
    }

    public Iterator<T> iterator() {
        return this.result.iterator();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ChunkData)) {
            return false;
        } else {
            ChunkData<?> that = (ChunkData)obj;
            boolean contentEqual = this.result.equals(that.result);
            boolean pageableEqual = this.pageable.equals(that.pageable);
            return contentEqual && pageableEqual;
        }
    }

    public int hashCode() {
        int result = 17;
        result += 31 * this.pageable.hashCode();
        result += 31 * this.result.hashCode();
        return result;
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        Assert.notNull(converter, "Function must not be null");
        Stream<T> var10000 = this.stream();
        Objects.requireNonNull(converter);
        Objects.requireNonNull(converter);
        return (List)var10000.map(converter::apply).collect(Collectors.toList());
    }
}

