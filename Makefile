# ----------------------------------------
# Makefile for WarehouseData Project
# ----------------------------------------

APP_SERVICE ?= app
DB_SERVICE ?= db

.PHONY: help up down test coverage build all

# ----------------------------
# Help target
# ----------------------------
help:  ## Show all available commands
	@echo ""
	@echo "WarehouseData Makefile Commands:"
	@echo "--------------------------------"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| sort \
		| awk 'BEGIN {FS = ":.*?## "}; {printf "  %-10s %s\n", $$1, $$2}'
	@echo ""

# ----------------------------
# Docker targets
# ----------------------------
up:  ## Start the app and database containers in detached mode
	docker-compose up -d

down:  ## Stop and remove the containers
	docker-compose down

# ----------------------------
# Build targets
# ----------------------------
build:  ## Build the Docker image for the app
	docker build -t warehousedata-app .

# ----------------------------
# All-in-one
# ----------------------------
all: up build  ## Start everything and build image
