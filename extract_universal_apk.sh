#!/usr/bin/env bash

SOURCE_DIR="./app/build/releases/*"
BUNDLE_TOOL="./tools/bundletool-all-0.13.0.jar"
BUNDLE_TOOL_DOWNLOAD_URL="https://github.com/google/bundletool/releases/download/0.13.0/bundletool-all-0.13.0.jar"

curl --location --create-dirs -o $BUNDLE_TOOL $BUNDLE_TOOL_DOWNLOAD_URL

# app/build/releases 配下に aab が書き出されるのでそこから apk を bundletool で書き出し
aabs=`find $SOURCE_DIR -type f -name *.aab`
for aab in $aabs
do
    dirName=`dirName $aab`
    fileName=`basename $aab`

    java -jar $BUNDLE_TOOL build-apks --mode=universal --bundle=$aab --output=$dirName/${fileName%.*}.apks --ks=./app/keystore/debug.keystore --ks-pass=pass:android --ks-key-alias=androiddebugkey --key-pass=pass:android
    unzip -o $dirName/${fileName%.*}.apks -d $dirName/
    mv $dirName/universal.apk $dirName/${fileName%.*}.apk

    rm $dirName/${fileName%.*}.apks
    rm $dirName/toc.pb
done

if [ -e $BUNDLE_TOOL ]; then
    rm -rf "./tools"
fi
