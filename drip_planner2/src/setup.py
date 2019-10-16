from setuptools import setup, find_packages

setup (
       name='drip_planner2',
       version='0.1',
       packages=find_packages(),

       # Declare your packages' dependencies here, for eg:
       install_requires=['matplotlib>=3.1.1'],

       # Fill in these to make your Egg ready for upload to
       # PyPI
       author='S. Koulouzis',
       author_email='',

       #summary = 'Just another Python package for the cheese shop',
       url='',
       license='',
       long_description='Long description of the package',

       # could also include long_description, download_url, classifiers, etc.

  
       )