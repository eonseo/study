#include <iostream>
#include <vector>
using namespace std;

#define N 3
vector<int> arr = {1, 2, 3};
vector<int> permutation;

void dfs(int visited) {
	// 모든 원소를 다 골랐다면 출력
	if (__builtin_popcount(visited) == N) {
		for (int x : permutation) cout << x << " ";
		cout << endl;
		return;
	}

	for (int i = 0; i < N; i++) {
		if (!(visited & (1 << i))) { // i 번째 숫자를 선택안했다면 선택
			permutation.push_back(arr[i]);
			dfs(visited | (1 << i)); // i 번째를 쓴 상태로 재귀
			permutation.pop_back();
		}
	}
}

int main() {
	dfs(0);
}
