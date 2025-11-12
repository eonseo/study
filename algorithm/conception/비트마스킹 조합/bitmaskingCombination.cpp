#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

#define N 4
#define K 2

vector<int> arr = {1, 2, 3, 4};
vector<int> combination;

void dfs_combination(int visited, int start)
{
    // 종료 조건: k 개 원소를 모두 골랐을 경우
    if (__builtin_popcount(visited) == K)
    {
        for (int x : combination)
        {
            cout << x << " ";
        }
        cout << endl;
        return;
    }

    // 남은 원소의 개수가 더 골라야하는 원소의 개수보다 작을 경우 가지치기
    if (N - start < K - combination.size())
    {
        return;
    }

    for (int i = start; i < N; i++)
    {
        if (!(visited & (1 << i)))
        {
            combination.push_back(arr[i]);
            dfs_combination(visited | (1 << i), i + 1);
            combination.pop_back();
        }
    }
}

int main()
{
    cout << N << "개 중 " << K << "개를 고르는 조합: " << endl;
    dfs_combination(0, 0);
    return 0;
}