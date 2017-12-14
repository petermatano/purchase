NAME = squaretrade/purchase
VERSION = 0.0.2

.PHONY: all build build-nocache test tag_latest release

all: build

build:
	docker build --no-cache -t $(NAME):$(VERSION) --rm .
	
run:
	docker run -it --rm -p 8080:8080 $(NAME):$(VERSION)

build-nocache:
	docker build -t $(NAME):$(VERSION) --no-cache --rm .

tag_latest:
	docker tag -f $(NAME):$(VERSION) $(NAME):latest

release: build tag_latest
	@if ! docker images $(NAME) | awk '{ print $$2 }' | grep -q -F $(VERSION); then echo "$(NAME) version $(VERSION) is not yet built. Please run 'make build'"; false; fi
	docker push $(NAME)
	@echo "*** Don't forget to run 'twgit release/hotfix finish' :)"