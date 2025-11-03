/*
    A형 화이팅 !!
*/

#include <iostream>
#include <algorithm>
#include <limits.h>
using namespace std;

int n, one, two, result, maxx;
int tree[100];

void init()
{
    one = 0;
    two = 0;
    result = 0;
    maxx = INT_MIN;
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
        for (int i = 0; i < n; i++)
        {
            cin >> tree[i];
            maxx = max(maxx, tree[i]);
        }
        for (int i = 0; i < n; i++)
        {
            one += ((maxx - tree[i]) % 2);
            two += ((maxx - tree[i]) / 2);
        }
        while ((two - one) > 1)
        {
            two--;
            one += 2;
        }
        if (one > two)
            result = one * 2 - 1;
        else
            result = two * 2;
        cout << "#" << tc << " " << result << "\n";
    }
}