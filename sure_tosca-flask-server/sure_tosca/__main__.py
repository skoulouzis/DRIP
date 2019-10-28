#!/usr/bin/env python

import connexion

from sure_tosca import encoder


def main():
    app = connexion.App(__name__, specification_dir='./swagger/')
    app.app.json_encoder = encoder.JSONEncoder
    app.add_api('swagger.yaml', arguments={'title': 'tosca-sure'})
    app.run(port=8081)


if __name__ == '__main__':
    main()
