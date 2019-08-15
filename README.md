# statsassignment

## Acceptable "action" JsON objects:
1) must include both "action" and "time" values (missing/null/empty not allowed)
2) Time values must be natural numbers (0 or greater)
3) Time values must be <= INTEGER.MAX_INT

## results
1) sorted by key
2) One significant digit (double, not int: this differs from example)
3) Totals are not allowed to roll over MAX_INT size

