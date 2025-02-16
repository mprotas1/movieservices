FROM golang:1.19
WORKDIR /app
COPY main.go .
RUN go build -o pricing-service
CMD ["/app/pricing-service"]