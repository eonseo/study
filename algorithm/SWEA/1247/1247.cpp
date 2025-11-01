#include <iostream>
#include <vector>
#include <limits.h>
using namespace std;

pair<int, int> *client;
pair<int, int> company;
pair<int, int> house;
pair<int, int> now;
int n, minn;

void init() {
	client = new pair<int, int>[n];
	minn = INT_MAX;
}

void destroy() {
	delete[] client;
}

void dfs(int visited, int distance, pair<int, int> now) {
	if(__builtin_popcount(visited) == n) {
		distance += (abs(now.first - house.first) + abs(now.second - house.second));
		minn = min(distance, minn);
		return;
	}
	// n 개의 클라이언트 주소를 돌면서 순열을 만들기
	// 하나의 고객을 방문할 때마다 거리 계산하기
	for (int i = 0; i < n; i++) {
		if (!(visited & (1 << i))) {
			dfs(visited | (1 << i), distance + (abs(now.first - client[i].first) + abs(now.second - client[i].second)), client[i]);
		}
	}
}

int main() {
	ios::sync_with_stdio(false); cin.tie(NULL);
	int t, tmpX, tmpY;

	cin >> t;
	for (int tc = 1; tc <= t; tc++) {
		cin >> n;
		init();
		cin >> company.first >> company.second;
		cin >> house.first >> house.second;
		for (int i = 0; i < n; i++) {
			cin >> client[i].first >> client[i].second;
		}
		dfs(0, 0, company);
		cout << "#" << tc << " " << minn << "\n";
		destroy();
	}
	return 0;
}