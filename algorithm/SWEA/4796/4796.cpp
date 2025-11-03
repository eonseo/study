/*
    오늘의 메모
    필요없는 변수만 줄여도 시간이 확 줄어든다.
    불필요한 초기화, 불필요한 변수 선언을 줄이자.
*/

#include <iostream>
using namespace std;

int n, height[50000];
int first, mid, last, result;

void init()
{
    mid = -1;
    last = -1;
}

int solve()
{
    int result = 0;
    int i = 0;
    while (i < n - 1)
    {
        init();
        while (i < n && height[i] > height[i + 1])
            i++;
        first = i;
        while (i < n && height[i] < height[i + 1])
            i++;
        mid = i;
        while (i < n - 1 && height[i] > height[i + 1])
            i++;
        last = i;
        if (mid != -1 && last != -1)
        {
            result += ((mid - first) * (last - mid));
            first = last;
        }
    }
    return result;
}

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    int t;

    cin >> t;
    for (int tc = 1; tc <= t; tc++)
    {
        cin >> n;
        for (int i = 0; i < n; i++)
            cin >> height[i];
        cout << "#" << tc << " " << solve() << "\n";
    }
    return 0;
}