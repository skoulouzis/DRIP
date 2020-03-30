# coding: utf-8

"""
    SEMAPHORE

    Semaphore API  # noqa: E501

    OpenAPI spec version: 2.2.0
    
    Generated by: https://github.com/semaphore-api/semaphore-codegen.git
"""


from __future__ import absolute_import

import unittest

import semaphore_client
from semaphore_client.api.user_api import UserApi  # noqa: E501
from semaphore_client.rest import ApiException


class TestUserApi(unittest.TestCase):
    """UserApi unit test stubs"""

    def setUp(self):
        self.api = semaphore_client.api.user_api.UserApi()  # noqa: E501

    def tearDown(self):
        pass

    def test_user_get(self):
        """Test case for user_get

        Fetch logged in user  # noqa: E501
        """
        pass

    def test_user_tokens_api_token_id_delete(self):
        """Test case for user_tokens_api_token_id_delete

        Expires API token  # noqa: E501
        """
        pass

    def test_user_tokens_get(self):
        """Test case for user_tokens_get

        Fetch API tokens for user  # noqa: E501
        """
        pass

    def test_user_tokens_post(self):
        """Test case for user_tokens_post

        Create an API token  # noqa: E501
        """
        pass

    def test_users_get(self):
        """Test case for users_get

        Fetches all users  # noqa: E501
        """
        pass

    def test_users_post(self):
        """Test case for users_post

        Creates a user  # noqa: E501
        """
        pass

    def test_users_user_id_delete(self):
        """Test case for users_user_id_delete

        Deletes user  # noqa: E501
        """
        pass

    def test_users_user_id_get(self):
        """Test case for users_user_id_get

        Fetches a user profile  # noqa: E501
        """
        pass

    def test_users_user_id_password_post(self):
        """Test case for users_user_id_password_post

        Updates user password  # noqa: E501
        """
        pass

    def test_users_user_id_put(self):
        """Test case for users_user_id_put

        Updates user details  # noqa: E501
        """
        pass


if __name__ == '__main__':
    unittest.main()
