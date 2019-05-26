# EXE8-Producer&Consumer
## Container spec
Container will save all Producer request, waiting for Consumer to get.

If the waiting list is smaller than item wall, FIFO will be performed, otherwise LIFO.

Default param:
- Producer listen port: `1926`
- Consumer listen port: `8170`
- Timeout: 10s
- Item wall: 10

## Producer spec
Producer will produce request to container.

Default param:
- Period: 1s
- Format: `[port].[count]`

## Consumer spec
Consumer will consume request from container.

Default param:
- Period: 3s if not empty, otherwise 2s

## Test
Container must have only one instance, producer and consumer have no specific limitation.
