### install aws credentials helper (ubuntu 19+)
```
sudo apt install amazon-ecr-credential-helper
```

### Create docker config and aws creds in user home directory
```
mkdir ~/.aws
mkdir ~/.docker
```

### create .aws/credentials file
vim ~/.aws/credentials
```
[default]
aws_access_key_id=
aws_secret_access_key=
```

### create .aws/config file
vim ~/.aws/config
```
[default]
region=ap-southeast-1
output=json
```

### create .docker/config.json file
vim ~/.docker/config.json
```
{
    "credsStore": "ecr-login"
}
```
