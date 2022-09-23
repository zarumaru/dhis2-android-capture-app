#!/bin/bash
set -ex

# Download compile tools, and SDK
echo "yes" | sdkmanager "emulator" "platform-tools"
echo "yes" | sdkmanager "build-tools;30.0.3"
echo "yes" | sdkmanager "platforms;android-30"
