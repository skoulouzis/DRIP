FROM python:3.7-buster

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY requirements.txt /usr/src/app/

RUN pip install --no-cache-dir -r requirements.txt
RUN pip install --no-cache-dir -r test-requirements.txt

COPY . /usr/src/app

RUN python -m unittest discover

EXPOSE 8081

ENTRYPOINT ["python"]

CMD ["-m", "sure_tosca"]
