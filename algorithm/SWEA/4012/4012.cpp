#include <iostream>
#include <algorithm>
#include <limits.h>
#include <cstring>
using namespace std;

int n, answer;
int **synergy;

void init()
{
    synergy = new int *[n];
    for (int i = 0; i < n; i++)
    {
        synergy[i] = new int[n];
        fill(synergy[i], synergy[i] + n, 0);
    }
    answer = INT_MAX;
}

void destroy()
{
    for (int i = 0; i < n; i++)
        delete[] synergy[i];
    delete[] synergy;
}

void combination(int visited, int start, int sumA)
{
    int popped = __builtin_popcount(visited);
    if (popped > n / 2)
        return;
    if (popped == n / 2)
    {
        int sumB = 0;
        for (int i = 0; i < n - 1; i++)
        {
            if (!(visited & (1 << i)))
            {
                for (int j = i + 1; j < n; j++)
                {
                    if (!(visited & (1 << j)))
                        sumB += synergy[i][j];
                }
            }
        }
        answer = min(answer, abs(sumA - sumB));
        return;
    }
    for (int i = start; i < n; i++)
    {
        if (!(visited & (1 << i)))
        {
            int newSumA = sumA;
            for (int j = 0; j < i; j++)
            {
                if ((visited & (1 << j)))
                    newSumA += synergy[min(i, j)][max(i, j)];
            }
            combination((visited | (1 << i)), i + 1, newSumA);
        }
    }
}

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int t, tmp;
    cin >> t;

    for (int tc = 1; tc <= t; tc++)
    {
        cin >> n;
        init();
        for (int y = 0; y < n; y++)
        {
            for (int x = 0; x < n; x++)
            {
                cin >> tmp;
                if (y < x)
                    synergy[y][x] += tmp;
                else
                    synergy[x][y] += tmp;
            }
        }
        combination(0, 0, 0);
        cout << "#" << tc << " " << answer << "\n";
        destroy();
    }
    return 0;
}