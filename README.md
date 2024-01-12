# java-gradle-project-best-practice
マルチプロジェクト構成のGradle Projectのサンプルです。

Gradleは自由度が高く、どのようにライブラリを管理するか、共通の設定をどこに置くか悩むことが多いと思います。  
実際に運用する中で「この設定を入れておくと便利」といったものを詰め込んだものをサンプルとして置いておこうという趣旨のリポジトリです。

おすすめの設定などあればPRお願いします。
# プロジェクト構成
|project|説明|
|---|---|
|:core| mainがあるプロジェクト。FizzBuzzを実行してDisplay#print(String)を呼び出します |
|:extensions:base|Displayインターフェースを定義していて、文字列の表示を抽象化します|
|:extensions:console|Displayの実装。渡された文字列を標準出力に吐き出します|

# 入れている設定
## VersionCatalog
see: https://docs.gradle.org/current/userguide/platforms.html  
build.gradleに`implements("org.codehaus.groovy:groovy:3.0.5")`のように宣言するのではなく、TOMLに宣言をまとめるようにしている。  
このプロジェクトの場合は `gradle/libs.versions.toml` にまとめてある。tomlに宣言するとbuild.gradleでは `libs.lombok` のように参照できる。

ただし、buildSrc以下では参照できないので`buildSrc/build.gradle.kts`, `buildSrc/settings.gradle.kts`, `buildSrc/src/main/kotlin/VersionCatalog.kt`のような設定を追加で書く必要がある。

## Locking dependency versions
see: https://docs.gradle.org/current/userguide/dependency_locking.html
ライブラリのバージョンを固定する仕組み。jsで言うところのpackage-lock.json的なもの。  
各プロジェクト毎にgradle.lockfileが作られる。  
複数のプロジェクトにまたがるため、buildSrc以下のConventionPluginに設定を入れている。

lockfile全体を更新する場合は `./gradlew resolveAndLockAll --write-locks` のようにタスクを実行する。  
ライブラリを指定して更新する場合は`./gradlew resolveAndLockAll --update-locks org.junit:junit-bom`のようにタスクを実行する

## ConventionPlugin
see: https://docs.gradle.org/current/samples/sample_convention_plugins.html  
Gradleで共通の設定を扱うための仕組み。  
buildSrc以下に共通の設定を書いてDRYにできる。

## --scanオプションの禁止
see: https://scans.gradle.com/  
`gradle build --scan` のようにscanオプションをつけるとビルド時の情報の詳細をWebで見れるようになっている。  
OSSでは便利だが、業務などのprivateなプロジェクトでは危険なため、--scanオプションが入っている場合は例外を投げて落とすようにするとよい。
```kotlin
if (gradle.startParameter.isBuildScan) {
    throw RuntimeException("--scanは禁止")
}
```

## Xlintを入れる
see: https://docs.oracle.com/en/java/javase/21/docs/specs/man/javac.html#option-Xlint-custom  
Javaコンパイラの警告。非推奨の警告やrawtypeなどの基本的な好ましくないコードの指摘をしてくれるので、-Xlint:allで全て有効にしておく。  
開発の中で不要だと思うものは`@SuppressWarnings`したり、除外していくと良い。  
複数のプロジェクトにまたがるため、buildSrc以下のConventionPluginに設定を入れている。  
```kotlin
tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:all")
}
```

## toolchainsを使う
see: https://docs.gradle.org/current/userguide/toolchains.html  
java.toolchain.languageVersionを指定すると、実行したマシンの中で対応するバージョンのJDKを探し、それを使って実行する。  
ローカルにインストールされたJDKの検出にはSDKMANやasdf-vmなどが使われる。

対応するJDKが見つからない場合、`org.gradle.toolchains.foojay-resolver-convention` を入れておくことで自動でJDKをおとしてくれる。

## spotbugs-gradle-plugin
see: https://github.com/spotbugs/spotbugs-gradle-plugin  
SpotBugsを実行するためのプラグイン。NPEになりそうな場所などを指摘してくれるツール。  
複数のプロジェクトにまたがるため、buildSrc以下のConventionPluginに設定を入れている。  

## spotless
see: https://github.com/diffplug/spotless  
フォーマッター。  
複数のプロジェクトにまたがるため、buildSrc以下のConventionPluginに設定を入れている。