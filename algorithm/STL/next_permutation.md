# std::next_permutation

Defined in header `<algorithm>`

```cpp
template <class BidirIt>
bool next_permutation(BidirIt first, BidirIt last);
```

```cpp
template <class BidirIt, class Compare>
bool next_permutation(BidirIt first, BidirIt last, Compare comp);
```

## Definition

- 지정된 범위 first - last 를 다음 순열로 바꿈.
- comp 로 지정한 비교 함수를 사용해서, 그 기준에 따라 사전순 정렬

## Return Value

다음 순열이 존재하면(새로운 순열이 이전 순열보다 사전식으로 더 큰 경우) `true` 반환, 존재하지 않으면 첫 번째 순열로 변환 후 `false` 반환

## Parameters

| 매개변수      | 설명                                                                                                   |
| ------------- | ------------------------------------------------------------------------------------------------------ |
| `first, last` | 순열을 만들 구간 `[first, last)`의 시작과 끝 반복자                                                    |
| `comp`        | 두 요소를 비교할 때 사용하는 함수 객체.<br> `comp(a, b)`가 `true`를 반환하면, “a가 b보다 작다”는 의미. |

## Complexity

`N = std::distance(first, last)`일 때,

- 최대 $N/2$의 swap 이 발생할 수 있음

## Exceptions

- 반복자 연산이나 swap 시 예외 발생할 수 있음

## 코드 뜯어보기

```cpp
// 양방향 반복자(Bidirectional Iterator) 타입인 _BidIt 하나만 템플릿 인수로 받음
// inline 키워드는 컴파일러에게 메모리 점프 대신 이 함수를 호출하는 곳에 함수의 본문을 직접 삽입하도록 권장
template<class _BidIt> inline
	bool next_permutation(_BidIt _First, _BidIt _Last)
	{	// permute and test for pure ascending, using operator<
	return (_STD next_permutation(_First, _Last, less<>()));
	}
```

```cpp
// 양방향과 비교 함수 객체
template<class _BidIt, class _Pr> inline
	bool next_permutation(_BidIt _First, _BidIt _Last, _Pr _Pred)
	{	// permute and test for pure ascending, using _Pred
	_Adl_verify_range(_First, _Last); // 디버그 모드 등에서 반복자 범위의 유효성을 검사하는 매크로 함수(실제로직에는 영향 X)
	auto _UFirst = _Get_unwrapped(_First); // 내부적으로 반복자를 최적화된 unwrapped 포인터나 반복자 형태로 변환
    // _First 가 _Last 보다 앞에 있는지, 두 반복자가 같은 컨테이너를 가리키고 있는지 등 확인
	const auto _ULast = _Get_unwrapped(_Last); // 불필요한 반복자 껍데기를 벗겨내고(언래핑) 포인터 연산을 사용하여 실행속도 극대화
	auto _UNext = _ULast;
    // 반복자를 하나 감소시킨 후 시작 반복자와 같다면 요소가 하나뿐이라는 의미. = 다음 순열이 없으므로 false 반환
	if (_UFirst == _ULast || _UFirst == --_UNext)
		{
		return (false);
		}

    //핵심루프: 다음 순열 찾기
	for (;;)
		{	// find rightmost element smaller than successor
		auto _UNext1 = _UNext;

        // 1. 피봇 찾기 단계
        // Pred 를 사용하여 a < b 인지 검사
        // 오른쪽부터 스캔하면서, 자신의 바로 오른쪽 요소보다 작은 가장 오른쪽 요소(= pivot 요소) 찾기. 이 피봇 요소를 *_UNext 에 위치
		if (_DEBUG_LT_PRED(_Pred, *--_UNext, *_UNext1))
			{	// swap with rightmost element that's smaller, flip suffix
            // 2. 다음 순열을 찾았을 때(성공) 핵심 로직 실행
            // 교환 대상 찾기, 교환, 뒤집기
			auto _UMid = _ULast;
			do
				{
				--_UMid;
				}
			while (!_DEBUG_LT_PRED(_Pred, *_UNext, *_UMid));

			_STD iter_swap(_UNext, _UMid);
			_Reverse_unchecked(_UNext1, _ULast);
			return (true);
			}

        // 3. 다음 순열이 없을 때(루프 종료조건)
		if (_UNext == _UFirst)
			{	// pure descending, flip all
			_Reverse_unchecked(_UFirst, _ULast);
			return (false);
			}
		}
	}
```

| 변수      | 코드에서의 위치               | 역할 (쉬운 설명)                                                            |
| :-------- | :---------------------------- | :-------------------------------------------------------------------------- |
| `_UNext`  | 루프 시작 시 `_ULast` 근처    | **피봇(Pivot) 후보**를 가리키며, 오른쪽에서 왼쪽으로 스캔하면서 이동합니다. |
| `_UNext1` | `_UNext`가 이동하기 전의 위치 | 피봇의 **바로 오른쪽 요소**를 가리킵니다. (나중에 뒤집기 시작점)            |
| `_UMid`   | `_ULast` (끝) 근처            | 피봇과 **교환할 대상**을 오른쪽에서부터 스캔하며 찾습니다.                  |
| `_UFirst` | 범위의 시작 (`begin()`)       | 루프 종료 조건 (`_UNext`가 여기까지 왔는지)을 확인합니다.                   |
| `_ULast`  | 범위의 끝 (`end()`)           | 스캔 종료 지점 및 뒤집기 종료 지점입니다.                                   |

**참고자료**
https://en.cppreference.com/w/cpp/algorithm/next_permutation.html
