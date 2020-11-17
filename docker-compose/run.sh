#!/bin/bash

apt update -y
apt install openssh-server -y
service ssh start

getent passwd vm_user > /dev/null 2&>1

if [ $? -eq 0 ]; then
    echo "user exists"
else
    useradd vm_user -s /bin/bash -m 
fi



mkdir -p /home/vm_user/.ssh && touch /home/vm_user/.ssh/authorized_keys

echo 'c3NoLXJzYSBBQUFBQjNOemFDMXljMkVBQUFBREFRQUJBQUFBZ1FDSUJpNHRYeFZtaXdNL01YWWE5VEZRS1FRMjFIWjg0SnpReFRrai9PcXVoTkwwdVZmeWVHUFN6RUFVRnV5bWMrWk43cUJWRkhtOCtlSkpYd1pGRENoeENuWjBWZHFNS1ZzQkZWK2QxK3ZESXllZ0k4djBSVm42alFXOEV5UnlzTEprckRCdkdLa1UxcDNzNkVQMTEyVExZU3J2UXRYZHVMVHhONTNZMGY3QmV3PT0gZ2VuZXJhdGVkIHVzZXIgYWNjZWVzIGtleXMK' | base64 -d >> /home/vm_user/.ssh/authorized_keys

cat /home/vm_user/.ssh/authorized_keys

tail -f /dev/null
