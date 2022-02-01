sudo docker build . -t himal77/nodeapp
sudo docker push himal77/nodeapp
wsk action create nodeapp --docker himal77/nodeapp
wsk action invoke nodeapp --result