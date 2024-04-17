import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFXブラウザを試してみました。");

        // タブペインの作成
        TabPane tabPane = new TabPane();

        // 新しいタブを追加するメソッド
        Button newTabButton = new Button("+");
        newTabButton.setOnAction(e -> addTab(tabPane));

        // レイアウトの設定
        BorderPane root = new BorderPane();
        root.setTop(newTabButton);
        root.setCenter(tabPane);

        // 初期タブの追加
        addTab(tabPane);

        // シーンの作成と表示
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void addTab(TabPane tabPane) {
        // タブの作成
        Tab tab = new Tab("New Tab");
        WebView webView = new WebView();
        tab.setContent(webView);

        // ナビゲーションバーの作成
        TextField urlField = new TextField("http://www.google.com");
        Button goButton = new Button("Go");
        Button backButton = new Button("Back");
        Button forwardButton = new Button("Forward");

        // Goボタンのイベントハンドラ
        goButton.setOnAction(e -> webView.getEngine().load(urlField.getText()));

        // Backボタンのイベントハンドラ
        backButton.setOnAction(e -> {
            if (webView.getEngine().getHistory().getCurrentIndex() > 0) {
                webView.getEngine().getHistory().go(-1);
            }
        });

        // Forwardボタンのイベントハンドラ
        forwardButton.setOnAction(e -> {
            if (webView.getEngine().getHistory()
                    .getCurrentIndex() < webView.getEngine().getHistory().getEntries().size() - 1) {
                webView.getEngine().getHistory().go(1);
            }
        });

        // ナビゲーションバーをHBoxに配置
        HBox navBar = new HBox();
        navBar.getChildren().addAll(backButton, forwardButton, urlField, goButton);

        // レイアウトの設定
        BorderPane root = new BorderPane();
        root.setTop(navBar);
        root.setCenter(webView);

        // タブのレイアウトをBorderPaneで設定
        BorderPane tabLayout = new BorderPane();
        tabLayout.setTop(navBar);
        tabLayout.setCenter(webView);

        // タブの内容をセット
        tab.setContent(tabLayout);

        // タブをタブペインに追加
        tabPane.getTabs().add(tab);

        // 初期ページのロード
        webView.getEngine().load(urlField.getText());

        // 新しいタブを選択状態にする
        tabPane.getSelectionModel().select(tab);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
