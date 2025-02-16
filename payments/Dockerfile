FROM golang:1.19
WORKDIR /app
COPY main.go .
RUN go build -o payments-service
CMD ["/app/payments-service"]