package com.test.webtest.domain.webvitals.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "web_vitals")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class WebVitalsEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "test_id", nullable = false, unique = true)
    private UUID testId;
    
    @Column(name = "lcp")
    private Double lcp;  // Largest Contentful Paint (ms)
    
    @Column(name = "cls")
    private Double cls;  // Cumulative Layout Shift (0-1)
    
    @Column(name = "inp")
    private Double inp;  // Interaction to Next Paint (ms)
    
    @Column(name = "fcp")
    private Double fcp;  // First Contentful Paint (ms)
    
    @Column(name = "tbt")
    private Double tbt;  // Total Blocking Time (ms)
    
    @Column(name = "ttfb")
    private Double ttfb; // Time to First Byte (ms)
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}
