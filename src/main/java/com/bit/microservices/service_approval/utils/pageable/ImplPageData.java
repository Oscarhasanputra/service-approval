package com.bit.microservices.service_approval.utils.pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.function.Function;

public abstract class ImplPageData<T> extends ChunkData<T> implements PageData<T> {
    private static final long serialVersionUID = 867755909294344406L;
    private long total;

    public ImplPageData(List<T> content, Pageable pageable, long total) {
        super(content, pageable,total);
        this.total = total;
    }

    public ImplPageData(List<T> content) {
        this(content, Pageable.unpaged(), null == content ? 0L : (long)content.size());
    }

    public ImplPageData(List<T> content, Pageable pageable) {
        super(content, pageable,content.size());
    }

    public int getTotalPages() {
        return this.getSize() == 0 ? 1 : (int)Math.ceil((double)this.total / (double)this.getSize());
    }

    public long getTotalElements() {
        return this.total;
    }

    public boolean hasNext() {
        return this.getNumber() + 1 < this.getTotalPages();
    }


    public boolean isLast() {
        return !this.hasNext();
    }


    public String toString() {
        String contentType = "UNKNOWN";
        List<T> content = this.getResult();
        if (!content.isEmpty() && content.get(0) != null) {
            contentType = content.get(0).getClass().getName();
        }

        return String.format("Page %s of %d containing %s instances", this.getNumber(), this.getTotalPages(), contentType);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ChunkData<?>)) {
            return false;
        } else {
            com.bit.microservices.utils.ImplPageCustom<?> that = (com.bit.microservices.utils.ImplPageCustom<?>)obj;
            return this.total == that.getTotalElements() && super.equals(obj);
        }
    }

    public int hashCode() {
        int result = 17;
        result += 31 * (int)(this.total ^ this.total >>> 32);
        result += 31 * super.hashCode();
        return result;
    }

    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new PageImpl(this.getConvertedContent(converter), this.getPageable(), this.total);
    }
}

