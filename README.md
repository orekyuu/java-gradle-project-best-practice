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
