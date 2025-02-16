FROM golang:1.23
WORKDIR /app
COPY go.mod go.sum ./
RUN go mod tidy
COPY . .
RUN go build -o payments-service
CMD ["/app/payments-service"]