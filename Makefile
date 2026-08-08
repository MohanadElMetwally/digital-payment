run:
	chmod +x scripts/run.sh
	./scripts/run.sh $(ENV)

test:
	chmod +x scripts/test.sh
	./scripts/test.sh $(ENV)

deploy:
	chmod +x scripts/deploy.sh
	./scripts/deploy.sh