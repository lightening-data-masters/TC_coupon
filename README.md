# ✈️ 해외 여행 상품 추천사이트 - 쿠폰 발급

## Coupon 데이터베이스 ERD
<img src = "https://github.com/sadang-data-enginneering/TC_coupon/assets/106741517/848ecc7d-feb0-4a4e-bfd1-b2cc2ed76e5a"/>

## 쿠폰 발급 시스템 구조도
<img src = "https://github.com/sadang-data-enginneering/TC_coupon/assets/106741517/647d569c-40d3-4b9f-91a3-fab9461956b2"/>

## 쿠폰 이벤트 요구사항
- 이벤트 기간 내에만 발급이 가능해야 한다.
- 유저 당 1번의 쿠폰 발급 (중복 발급 ❌)
- 선착순 쿠폰의 최대 쿠폰 발급 수량을 설정한다.

## 쿠폰 발급 기능
- 쿠폰 발급 기간 검증
- 쿠폰 발급 수량 검증
  - 쿠폰 전체 발급 수량 확인
  - 중복 발급 요청 검증
- 쿠폰 발급
  - 쿠폰 발급 수량 증가
  - 쿠폰 발급 정보 저장
