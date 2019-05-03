FROM jenkins/jenkins:lts

ENV GRADLE_HOME /opt/gradle
ENV GRADLE_VERSION 5.4.1
ENV ANDROID_COMPILE_SDK 28
ENV ANDROID_BUILD_TOOLS 28.0.2
ENV ANDROID_SDK_TOOLS 4333796
ENV ANDROID_HOME /opt/android-sdk-linux
ENV PATH="${PATH}:/opt/gradle/gradle-5.4.1/bin:$ANDROID_HOME/platform-tools"

USER root
# Setup Jenkins Plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN echo "Installing Jenkins Plugins" \
    && /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt \
    && echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

# Install Gradle
RUN set -o errexit -o nounset \
    && echo "Downloading Gradle" \
    && mkdir "${GRADLE_HOME}" \
    && wget --no-verbose --output-document=gradle.zip "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" \
    && unzip -d "${GRADLE_HOME}" gradle.zip \
    && rm gradle.zip

# Install Android Platform Tools
RUN apt-get --quiet update --yes && \
    apt-get --quiet install --yes wget tar unzip lib32stdc++6 lib32z1 && \
    wget --quiet --output-document=android-sdk.zip https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip && \
    unzip -d "${ANDROID_HOME}" android-sdk.zip && \
    rm android-sdk.zip && \
    echo y | "${ANDROID_HOME}"/tools/bin/sdkmanager "platforms;android-${ANDROID_COMPILE_SDK}" >/dev/null && \
    echo y | "${ANDROID_HOME}"/tools/bin/sdkmanager "platform-tools" >/dev/null && \
    echo y | "${ANDROID_HOME}"/tools/bin/sdkmanager "build-tools;${ANDROID_BUILD_TOOLS}" >/dev/null &&\
    yes | "${ANDROID_HOME}"/tools/bin/sdkmanager --licenses &&\
    chown -R jenkins:jenkins "${ANDROID_HOME}"    

#Enable access to Android Tools for the Jenkins user
USER jenkins
RUN export PATH="${PATH}:/opt/gradle/gradle-5.4.1/bin:$ANDROID_HOME/platform-tools"

EXPOSE 80 8080