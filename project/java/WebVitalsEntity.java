package com.test.webtest.domain.webvitals.entity;

import com.test.webtest.domain.test.entity.TestEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

//WebVitalsEntity - Web Vitals 원본 지표 저장
//ERD 의 web_vitals 테이블과 매핑

@Entity
@Table(name = "web_vitals")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class WebVitalsEntity {
    @Id
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private TestEntity test;

    @Column(name = "lcp")
    private Double lcp;

    @Column(name = "cls")
    private Double cls;

    @Column(name = "inp")
    private Double inp;

    @Column(name = "fcp")
    private Double fcp;

    @Column(name = "tbt")
    private Double tbt;

    @Column(name = "ttfb")
    private Double ttfb;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public static WebVitalsEntity create(TestEntity test, Double lcp, Double cls, Double inp, Double fcp, Double tbt, Double ttfb) {
        validateMetricValue(lcp, "LCP");
        validateMetricValue(cls, "CLS");
        validateMetricValue(inp, "INP");
        validateMetricValue(fcp, "FCP");
        validateMetricValue(tbt, "TBT");
        validateMetricValue(ttfb, "TTFB");

        return WebVitalsEntity.builder()
                .test(test)
                .lcp(lcp)
                .cls(cls)
                .inp(inp)
                .fcp(fcp)
                .tbt(tbt)
                .ttfb(ttfb)
                .build();
    }

    private static void validateMetricValue(Double value, String metricName) {
        if (value == null) {
            return;
        }
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException(metricName + " 값은 NaN일 수 없습니다.");
        }
        if (value < 0) {
            throw new IllegalArgumentException(metricName + " 값은 음수일 수 없습니다. 입력값: " + value);
        }
    }
}
