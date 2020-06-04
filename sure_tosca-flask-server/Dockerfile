FROM python:3.6-buster

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY requirements.txt /usr/src/app/
COPY test-requirements.txt /usr/src/app/


RUN pip install --no-cache-dir -r requirements.txt
RUN pip install --no-cache-dir -r test-requirements.txt

COPY . /usr/src/app

RUN python -m unittest discover
RUN pip uninstall -y --no-cache-dir -r test-requirements.txt


EXPOSE 8081

ENTRYPOINT ["python"]

CMD ["-m", "sure_tosca"]
