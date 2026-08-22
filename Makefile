COMMAND_ARG := $(word 2,$(MAKECMDGOALS))

ENV := $(if $(COMMAND_ARG),$(COMMAND_ARG),dev)

ifneq ($(COMMAND_ARG),)
  $(eval $(COMMAND_ARG):;@:)
endif

run:
	chmod +x scripts/run.sh
	./scripts/run.sh $(ENV)

test:
	chmod +x scripts/test.sh
	./scripts/test.sh $(ENV)

deploy:
	chmod +x scripts/deploy.sh
	./scripts/deploy.sh