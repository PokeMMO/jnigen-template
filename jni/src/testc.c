#include "testc.h"

int32_t getCRand(int32_t n) {
	srand(time(0));
    return rand() % n;
}
