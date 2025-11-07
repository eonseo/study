/*
오늘의 메모 !

2의 제곱근이 되는 수를 구하는 줄 알았는데 그냥 제곱근이면 되는거였다 !!
*/

#include <iostream>
#include <cmath>
using namespace std;

long long n, tmp, result;

void init()
{
	result = 0;
}

void solve()
{
	while (n != 2)
	{
		tmp = sqrt(n);
		// 제곱근일 경우
		if (tmp * tmp == n)
		{
			result++;
			n = tmp;
		}
		// 제곱근이 아닐경우 가까운 제곱근까지 더하기
		else
		{
			tmp = (tmp + 1) * (tmp + 1);
			result += (tmp - n);
			n = tmp;
		}
	}
}

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(NULL);

	int t;
	cin >> t;

	for (int tc = 1; tc <= t; tc++)
	{
		init();
		cin >> n;
		solve();
		cout << "#" << tc << " " << result << "\n";
	}
	return 0;
}