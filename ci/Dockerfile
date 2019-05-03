FROM jenkins/jenkins:lts

ENV GRADLE_HOME /opt/gradle
ENV GRADLE_VERSION 5.4.1
ENV ANDROID_COMPILE_SDK 28
ENV ANDROID_BUILD_TOOLS 28.0.2
ENV ANDROID_SDK_TOOLS 4333796
ENV ANDROID_HOME /android-sdk-linux
ENV PATH="${PATH}:/opt/gradle/gradle-5.4.1/bin"

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



USER jenkins
EXPOSE 80 8080