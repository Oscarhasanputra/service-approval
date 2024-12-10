package com.bit.microservices.service_approval.utils.pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.function.Function;

public interface SliceData<T> extends Streamable<T> {
    int getNumber();

    int getSize();

    int getNumberOfElements();

    List<T> getResult();

    boolean hasResult();

    Sort getSort();

    boolean isFirst();

    boolean isLast();

    boolean hasNext();

    boolean hasPrevious();

    default Pageable getPageable() {
        return PageRequest.of(this.getNumber(), this.getSize(), this.getSort());
    }

    Pageable nextPageable();

    Pageable previousPageable();

    <U> Slice<U> map(Function<? super T, ? extends U> converter);

    default Pageable nextOrLastPageable() {
        return this.hasNext() ? this.nextPageable() : this.getPageable();
    }

    default Pageable previousOrFirstPageable() {
        return this.hasPrevious() ? this.previousPageable() : this.getPageable();
    }
}
