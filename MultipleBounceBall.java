import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;





public class MultipleBounceBall extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    MultipleBallPane ballPane = new MultipleBallPane();
    

    Button btAdd = new Button("+");
    Button btSubtract = new Button("-");
    HBox hBox = new HBox(10);
    hBox.getChildren().addAll(btAdd, btSubtract);
    hBox.setAlignment(Pos.CENTER);

    // Add or remove a ball
    btAdd.setOnAction(e -> ballPane.add());
    btSubtract.setOnAction(e -> ballPane.subtract());

    // Pause and resume animation
    ballPane.setOnMousePressed(e -> ballPane.pause());
    ballPane.setOnMouseReleased(e -> ballPane.play());

    
    
    
    BorderPane pane = new BorderPane();
    pane.setCenter(ballPane);
    
    pane.setBottom(hBox);

    // Create a scene and place the pane in the stage
    Scene scene = new Scene(pane, 250, 150);
    primaryStage.setTitle("MultipleBounceBall"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  private class MultipleBallPane extends Pane {
    private Timeline animation;

    public MultipleBallPane() {
      // Create an animation for moving the ball
      animation = new Timeline(
        new KeyFrame(Duration.millis(50), e -> moveBall()));
      animation.setCycleCount(Timeline.INDEFINITE);
      animation.play(); // Start animation
    }

    public void add() {
      Color color = new Color(Math.random(), Math.random(), Math.random(), 0.5);
      getChildren().add(new Ball(30, 30, 20* Math.random() , color)); 
    }
    
    public void subtract() {
      if (getChildren().size() > 0) {
        getChildren().remove(getChildren().size() - 1); 
      }
    }

    public void play() {
      animation.play();
    }

    public void pause() {
      animation.pause();
    }

    public void increaseSpeed() {
      animation.setRate(animation.getRate() + 0.1);
    }

    public void decreaseSpeed() {
      animation.setRate(
        animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
    }

    public DoubleProperty rateProperty() {
      return animation.rateProperty();
    }

    protected void moveBall() {
		Ball ball2 = null;
      for (Node node: this.getChildren()) {
        Ball ball = (Ball)node;
        // Check boundaries
		
		if(ball2 != null){
			for(Node node2 : this.getChildren()){
				ball2 = (Ball)node2;
       
				if(check(ball,ball2) == true){
					if(ball.dx != ball2.dx){
						ball.dx *= -1;
						ball2.dx *= -1;
					}
					if(ball.dy != ball2.dy){
						ball.dy *= -1;
						ball2.dy *= -1;
					}
					
				}
				
		
			}
			if (ball.getCenterX() < ball.getRadius() || 
				ball.getCenterX() > getWidth() - ball.getRadius() ){
					ball.dx *= -1;
			// Change ball move direction
			}
			if (ball.getCenterY() < ball.getRadius() || 
				ball.getCenterY() > getHeight() - ball.getRadius()) {
				ball.dy *= -1; // Change ball move direction
			}
			ball.setCenterX(ball.dx + ball.getCenterX());
			ball.setCenterY(ball.dy + ball.getCenterY());
		}else{
			if (ball.getCenterX() < ball.getRadius() || 
				ball.getCenterX() > getWidth() - ball.getRadius() ){
					ball.dx *= -1; // Change ball move direction
			}
			if (ball.getCenterY() < ball.getRadius() || 
				ball.getCenterY() > getHeight() - ball.getRadius()) {
					ball.dy *= -1; // Change ball move direction
			}
			ball.setCenterX(ball.dx + ball.getCenterX());
			ball.setCenterY(ball.dy + ball.getCenterY());
			ball2 = ball;
		}
		
        // Adjust ball position
        
      }
    }
  }
  public boolean check(Ball a, Ball b){
	  if(Math.pow(a.getCenterX()- b.getCenterX(),2) + Math.pow(a.getCenterY()- b.getCenterY(),2) <= Math.pow(a.getRadius()+b.getRadius(),2)
			){
		  return true;
	  }else{
	  return false;
	  }
  }

  class Ball extends Circle {
    private double dx = 1, dy = 1;

    Ball(double x, double y, double radius, Color color) {
      super(x, y, radius);
      setFill(color); // Set ball color
    }
  }
  
  
  public static void main(String[] args) {
    launch(args);
  }
}