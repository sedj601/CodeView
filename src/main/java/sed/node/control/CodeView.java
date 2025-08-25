/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sed.node.control;

import java.net.URISyntaxException;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author blj0011
 */
public class CodeView extends Region
{
    private String prismCssLocation;
    private String primsJavascriptLocation;
    private CodeLanguage codeLanguage;
    private String code;
    
    private String htmlString =
                        """
                        <!DOCTYPE html>
                        <html>
                            <head>
                                <link href="%s" rel="stylesheet" type="text/css"/>
                                <script src="%s" type="text/javascript"></script>
                            </head>
                            <body>
                                <div style="height: 100%%; overflow: auto;">
                                    <pre>
                                        <code class="%s">
                        %s
                                        </code>
                                    </pre>
                                </div>
                            </body>
                        </html>
                        """;
    
    public enum CodeLanguage
    {
        //todo - add support for other languages.
        JAVA("language-java");
        
        private final String jsCode;
        
        private CodeLanguage(String jsCode)
        {
            this.jsCode = jsCode;
        }
        
        public String getJsCode()
        {
            return this.jsCode;
        }
    }
    
    private final WebView webView = new WebView();
    
    private static final double PREFERRED_WIDTH = 500;
    private static final double PREFERRED_HEIGHT = 500;
    private static final double MINIMUM_WIDTH = 400;
    private static final double MINIMUM_HEIGHT = 400;
    private static final double MAXIMUM_WIDTH = Double.MAX_VALUE;
    private static final double MAXIMUM_HEIGHT = Double.MAX_VALUE;
    private double width;
    private double height;
    
    public CodeView(String code, CodeLanguage codeLanguage)
    {
        try 
        {
            this.prismCssLocation = getClass().getResource("prism.css").toURI().toString();
            this.primsJavascriptLocation = getClass().getResource("prism.js").toURI().toString();
            
            this.code = code;
            this.codeLanguage = codeLanguage;
            
            String htmlString2 = this.htmlString.formatted(prismCssLocation, primsJavascriptLocation, this.codeLanguage.getJsCode(), this.code);
            initGraphics(htmlString2);
            registerListeners();            
        } 
        catch (URISyntaxException ex) 
        {
            System.out.println(ex.toString());
        }
    }
    
    private void initGraphics(String htmlString2) 
    {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 || Double.compare(getWidth(), 0.0) <= 0 || Double.compare(getHeight(), 0.0) <= 0) 
        {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) 
            {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } 
            else 
            {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }        
       
        WebEngine engine = webView.getEngine();
        engine.setJavaScriptEnabled(true);
        engine.loadContent(htmlString2);
        webView.setMinSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        webView.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        webView.setMaxSize(MAXIMUM_WIDTH, MAXIMUM_HEIGHT);
        
        getChildren().setAll(webView);
    }
        
    // ******************** Methods *******************************************
    @Override protected double computeMinWidth(final double HEIGHT) { return MINIMUM_WIDTH; }
    @Override protected double computeMinHeight(final double WIDTH) { return MINIMUM_HEIGHT; }
    @Override protected double computePrefWidth(final double HEIGHT) { return super.computePrefWidth(HEIGHT); }
    @Override protected double computePrefHeight(final double WIDTH) { return super.computePrefHeight(WIDTH); }
    @Override protected double computeMaxWidth(final double HEIGHT) { return MAXIMUM_WIDTH; }
    @Override protected double computeMaxHeight(final double WIDTH) { return MAXIMUM_HEIGHT; }
    
    // ******************** Listeners *******************************************
    private void registerListeners() 
    {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
    }
    
    // ******************** Resizing ******************************************
    private void resize() 
    {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();

        if (width > 0 && height > 0)
        {
            webView.setPrefWidth(width);
            webView.setPrefHeight(height);
        }
    }
}
