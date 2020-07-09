package com.beatcorona.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Random;

public class BeatCorona extends ApplicationAdapter implements ApplicationListener {
	Services service;

	public BeatCorona(Services service){
		this.service = service;
	}

	Stage stage;
	Skin skin;
	Table menuRoot;

	SpriteBatch batch;
	Texture startPage;
	Texture instructions;
	Texture tapToProceed;
	Texture heart;
	//ShapeRenderer shapeRenderer;
	Texture gameover;
	Texture rules;
	Texture menuScreen;


	int stageDrawflag = 0;

	Texture[] background = new Texture[3];
	Texture[] clouds = new Texture[3];
	float[] backgroundX = new float[3];
	float[] cloudsX = new float[3];
	float backgroundVelocity = 4;
	float cloudVelocity = 1;
	Texture sun;

	Music music;
	//Music musicFast1;
	//Music musicFast2;
	//Music musicFast3;
	Sound soundDie;
	Sound collect;
	Sound jump;
	Sound coronaOverlap;

	Texture boy;
	Texture boyWithMask;
	Texture boyWithoutMask;
	//Texture boyWithMask2;
	//Texture boyWithoutMask2;

	float boyY;
	Rectangle boyRectangle;
	BitmapFont font;
	BitmapFont font1;
	int tapCount = 0;
	int deductedHealth = 1;
	int swapTime = 3000;

	int score = 0;
	int highScore = 0 ;
	int health = 100;
	int topScoringCorona = 0;
	int bottomScoringCorona = 0;
	float minDistBwCorona;
	int collidedTopVirusId = -1;
	int collidedBottomVirusId = -1;

	int virtualHeight = 1920;
	int virtualWidth = 1080;
	float aspectRatio = (float)virtualHeight/(float)virtualWidth;
	float aspectRatioCurr;
	float scale;

	float velocity = 0;
	int velocityFlag = 0;
	int gameState = 0 ;
	int acquiredState = 0;
	float gravity = 1.2f;
	//int count = 0;

	Texture topCoronaVirus;
	Texture bottomCoronaVirus;
	float gap;
	float maxCoronaOffset;
	Random randomGenerator;
	float distanceBetweenCorona;
	int numberOfCorona = 4;
	float[] topCoronaX = new float[numberOfCorona];
	float[] bottomCoronaX = new float[numberOfCorona];
	float[] topCoronaOffset = new float[numberOfCorona];
	float[] bottomCoronaOffset = new float[numberOfCorona];
	Circle[] topCoronaCircles;
	Circle[] bottomCoronaCircles;
	float topCoronaVelocity = 8;
	float bottomCoronaVelocity = 4;

	long currTime;
	long startTime;
	long currTimeEquipments;
	int topOrBottomflag = -1 ;


	Texture sanitizer;
	float sanitizerX;
	float randomHeightSanitizer;
	Rectangle sanitizerRectangle;

	Texture mask;
	float maskX;
	float randomHeightMask;
	Circle maskCircle;

	Texture socialDist;
	float socialDistX;
	float randomHeightSocialDist;
	Circle socialDistCircle;
	int playOnce;
	
	@Override
	public void create () {

		aspectRatioCurr = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();

		if(aspectRatioCurr > aspectRatio)
		{
			scale = (float)Gdx.graphics.getHeight()/(float)virtualHeight;
		}
		else
		{
			scale = (float)Gdx.graphics.getWidth()/(float)virtualWidth;
		}

		gap = 410*scale;
		minDistBwCorona = 300*scale;

		font = new BitmapFont();
		font.setColor(Color.RED);
		font.getData().setScale((float)7*scale);
		font1 = new BitmapFont();
		font1.setColor(Color.BLACK);
		font1.getData().setScale((float)6*scale);

		batch = new SpriteBatch();

		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
//		musicFast1 = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
//		musicFast1.setLooping(true);
//		musicFast2 = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
//		musicFast2.setLooping(true);
//		musicFast3 = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
//		musicFast3.setLooping(true);

		jump = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.wav"));
		soundDie = Gdx.audio.newSound(Gdx.files.internal("sfx_die.wav"));
		collect = Gdx.audio.newSound(Gdx.files.internal("sfx_point.wav"));
		coronaOverlap = Gdx.audio.newSound(Gdx.files.internal("sfx_hit.wav"));


		background[0] = new Texture("bg7.png");
		background[1] = new Texture("bg8.png");
		background[2] = new Texture("bg9.png");
		sun = new Texture("sun.png");
		clouds[0] = new Texture("cloud1.png");
		clouds[1] = new Texture("cloud2.png");
		clouds[2] = new Texture("cloud3.png");
		tapToProceed = new Texture("Taptoproceedfinal.png");

		gameover = new Texture("GameOverScreen.png");
		startPage = new Texture("startPage.png");
		instructions = new Texture("Instructions.png");
		rules = new Texture("rulePageFinal.png");
		heart = new Texture("heartFinal.PNG");
		menuScreen = new Texture("menuscreen.png");
		//shapeRenderer = new ShapeRenderer();


		sanitizer = new Texture("sanitizerOrange1.png");
		mask = new Texture("maskOrange1.png");
		socialDist = new Texture("quarantineOrange1.png");


		boyWithoutMask = new Texture("boyfinal.png");
		boyWithMask = new Texture("boywithmaskfinal.png");
		//boyWithoutMask2 = new Texture("boy2final.png");
		//boyWithMask2 = new Texture("boyWithMask2final.png");

		boy = boyWithoutMask;


		boyRectangle = new Rectangle();

		topCoronaVirus = new Texture("corona.png");
		bottomCoronaVirus = new Texture("corona.png");
		maxCoronaOffset = Gdx.graphics.getHeight()/2 - gap/2 - minDistBwCorona;
		randomGenerator = new Random();
		distanceBetweenCorona = Gdx.graphics.getWidth();   // keep it more than or equal to Gdx.graphics.getWidth()*3/4
		topCoronaCircles = new Circle[numberOfCorona];
		bottomCoronaCircles = new Circle[numberOfCorona];

		startTime = System.currentTimeMillis();


		startGame();

		stage = new Stage();

		skin = new Skin(Gdx.files.internal("glassy-ui.json"));

		Gdx.input.setInputProcessor(stage);

	}

	public void startGame() {
		playOnce=0;

		for(int i=0;i<3;i++) {
			backgroundX[i] = i * Gdx.graphics.getWidth();
			cloudsX[i] = i * Gdx.graphics.getWidth();
		}

		boyY = Gdx.graphics.getHeight()*0.70f - boy.getHeight()/2;

		int randomMaskX = randomGenerator.nextInt(3) + 1;
		maskX = Gdx.graphics.getWidth()/2  - mask.getWidth()*scale/2 + Gdx.graphics.getWidth() + distanceBetweenCorona*(1f/2 + randomMaskX);
		randomHeightMask = randomGenerator.nextFloat();
		maskCircle = new Circle();

		int randomSanitizerX = randomGenerator.nextInt(5) + 1;
		sanitizerX = Gdx.graphics.getWidth()/2  - sanitizer.getWidth()/2 + Gdx.graphics.getWidth() + distanceBetweenCorona*(1f/4 + randomSanitizerX);
		randomHeightSanitizer = randomGenerator.nextFloat();
		sanitizerRectangle = new Rectangle();

		int randomSocialDistX = randomGenerator.nextInt(7) + 1;
		socialDistX = Gdx.graphics.getWidth()/2 - socialDist.getWidth()/2 + Gdx.graphics.getWidth() + distanceBetweenCorona*(3f/4 + randomSocialDistX);
		randomHeightSocialDist = randomGenerator.nextFloat();
		socialDistCircle = new Circle();


		for(int i=0;i<numberOfCorona;i++){

			topCoronaOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 2*minDistBwCorona);
			bottomCoronaOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 2*minDistBwCorona);
			topCoronaX[i] = Gdx.graphics.getWidth()/2 - topCoronaVirus.getWidth()*scale/2 + Gdx.graphics.getWidth() + i * distanceBetweenCorona;
			bottomCoronaX[i] = Gdx.graphics.getWidth()/2 - bottomCoronaVirus.getWidth()*scale/2 + Gdx.graphics.getWidth() + i * distanceBetweenCorona;
			topCoronaCircles[i] = new Circle();
			bottomCoronaCircles[i] = new Circle();

		}
	}

	public void createStage() {

		menuRoot = new Table(skin);
		menuRoot.setFillParent(true);
		Table table = new Table(skin);

		final float BUTTON_WIDTH = 850*scale;
		TextButton z= new TextButton("Play Again",skin);
		z.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameState = 5;
				super.clicked(event, x, y);
			}
		});

		table.add(z).width(BUTTON_WIDTH).padBottom(30*scale);
		table.row();

		TextButton y= new TextButton("Challenge a Friend",skin);
		y.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				service.share(highScore);
				super.clicked(event, x, y);
			}
		});

		table.add(y).width(BUTTON_WIDTH).padBottom(30*scale);
		table.row();


		TextButton x= new TextButton("Share the App",skin);
		x.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				service.share();
				super.clicked(event, x, y);
			}
		});

		table.add(x).width(BUTTON_WIDTH).padBottom(30*scale);
		table.row();

		TextButton v = new TextButton("Rate the App",skin);
		v.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				service.rate();
				super.clicked(event, x, y);
			}
		});

		table.add(v).width(BUTTON_WIDTH).padBottom(30*scale);
		table.row();

		TextButton w = new TextButton("Exit",skin);
		w.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
				super.clicked(event, x, y);
			}
		});

		table.add(w).width(BUTTON_WIDTH).padBottom(30*scale);
		table.row();


		menuRoot.add(table);

		stage.clear();
		stage.addActor(menuRoot);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}

	public void calcScore () {
		if(score <= 30 && velocityFlag == 0) {
			//music.play();
			velocityFlag = 1;
		} else if (score > 30 && score <= 100 && velocityFlag == 1) {
			//music.stop();
			//musicFast1.play();
			topCoronaVelocity += 2;
			bottomCoronaVelocity += 2;
			velocityFlag = 2;
		} else if (score > 100 && score <= 400 && velocityFlag == 2) {
			//musicFast1.stop();
			//musicFast2.play();
			topCoronaVelocity += 2;
			bottomCoronaVelocity +=2;
			velocityFlag = 3;
		} else if (score > 400 && score <= 800 && velocityFlag == 3){
			//musicFast2.stop();
			//musicFast3.play();
			topCoronaVelocity += 2;
			bottomCoronaVelocity +=2;
			velocityFlag = 4;
		} else if (score > 800 && velocityFlag == 4 ) {
			topCoronaVelocity += 2;
			bottomCoronaVelocity += 2;
			velocityFlag = 5;
			deductedHealth = 2;
			swapTime = 2000;
		}
	}

	public  void detectIntersection() {
		if(Intersector.overlaps( maskCircle, boyRectangle) ) {
			collect.play();
			boy = boyWithMask;
			//count = 1;
			maskX -= 2 * distanceBetweenCorona;
			score += 5;
			acquiredState = 1;
			currTimeEquipments = System.currentTimeMillis();
		}

		if(Intersector.overlaps( boyRectangle, sanitizerRectangle) ) {
			collect.play();
			sanitizerX -= 2 * distanceBetweenCorona;
			score += 10;
			health += 10;
			if(health > 100) {
				health = 100;
			}
			//acquiredState=2;
		}

		if(Intersector.overlaps(socialDistCircle, boyRectangle) ) {
			collect.play();
			socialDistX -= 2 * distanceBetweenCorona;
			score+=20;
			health+=30;
			if(health>100) {
				health=100;
			}
			//acquiredState=3;
		}

//		if(acquiredState == 0)
//		{
//			if(count % 30 == 0) {
//				if (boy == boyWithoutMask) {
//					boy = boyWithoutMask2;
//				} else {
//					boy = boyWithoutMask;
//				}
//			}
//		}
		if(acquiredState == 1)
		{
			currTime = System.currentTimeMillis();
			if(currTime - currTimeEquipments  >= 10000)
			{
				boy = boyWithoutMask;
				acquiredState = 0;
				//count = 1;
				topOrBottomflag = -1;
			}

//			if(count % 30 == 0) {
//				if (boy == boyWithMask) {
//					boy = boyWithMask2;
//				} else {
//					boy = boyWithMask;
//				}
//			}

		}
		for(int i=0;i<numberOfCorona;i++) {

			if((Intersector.overlaps( topCoronaCircles[i], boyRectangle) ) ) {
				if(gameState == 1) {
					coronaOverlap.play(0.1f);
				}

				if(acquiredState == 0 && health > 0 && i != collidedTopVirusId && topOrBottomflag != 0) {
					health -= deductedHealth;
				} else if (acquiredState == 1 ) {
					boy = boyWithoutMask;
					collidedTopVirusId = i;
					topOrBottomflag = 0;
					acquiredState = 0;
					//count=1;
				}
			}
			if(( Intersector.overlaps( bottomCoronaCircles[i], boyRectangle)) ) {
				if(gameState == 1) {
					coronaOverlap.play(0.1f);
				}
				if(acquiredState == 0 && health > 0 && i != collidedBottomVirusId && topOrBottomflag != 1) {
					health -= deductedHealth;
				} else if (acquiredState == 1) {
					boy = boyWithoutMask;
					collidedBottomVirusId = i;
					topOrBottomflag = 1;
					acquiredState = 0;
					//count = 1;
				}

			}
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.app.log("Leak",String.valueOf(Gdx.app.getJavaHeap()/1048576));

		batch.begin();
		if(health <= 0) {
			gameState = 2;
			if (playOnce == 0) {
				soundDie.play(1.5f);
				playOnce = 1;
			}
		}
		if(gameState ==1) {
			music.play();
			calcScore();
//			count++;
//			if(count >= 1001) {
//				count = 1;
//			}

			for(int i=0;i<3;i++) {

				if(backgroundX[i] < -background[i].getWidth()) {
					backgroundX[i] += 3 * Gdx.graphics.getWidth();
				}

				backgroundX[i] -= backgroundVelocity;
				batch.draw(background[i], backgroundX[i], 0 , Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

			}

			batch.draw(sun,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

			for(int i=0;i<3;i++) {

				if(cloudsX[i] < -clouds[i].getWidth()*scale) {
					cloudsX[i] += 3 * Gdx.graphics.getWidth();
				}

				cloudsX[i] -= cloudVelocity;
				batch.draw(clouds[i], cloudsX[i], Gdx.graphics.getHeight()-1500*scale , Gdx.graphics.getWidth(),1500*scale);
			}

			batch.draw(heart,40*scale,Gdx.graphics.getHeight()-200*scale, (float)heart.getWidth()*scale,(float)heart.getHeight()*scale);


			currTime = System.currentTimeMillis();
			if(currTime - startTime >= swapTime) {
				float temp = topCoronaVelocity;
				topCoronaVelocity = bottomCoronaVelocity;
				bottomCoronaVelocity = temp;
				startTime = System.currentTimeMillis();
			}

			if(topCoronaX[topScoringCorona] < Gdx.graphics.getWidth()/4)
			{
				if(acquiredState == 1)
				{
					score += 2;
				}
				else {
					score += 1;
				}
				if(topScoringCorona < numberOfCorona-1) {
					topScoringCorona++;
				} else {
					topScoringCorona = 0;
				}
			}
			if(bottomCoronaX[bottomScoringCorona] < Gdx.graphics.getWidth()/4)
			{
				if(acquiredState == 1)
				{
					score += 2;
				}
				else {
					score += 1;
				}
				if(bottomScoringCorona < numberOfCorona-1) {
					bottomScoringCorona++;
				} else {
					bottomScoringCorona = 0;
				}
			}
			if(Gdx.input.justTouched()) {
				jump.play(0.5f);
				velocity = -20;
			}

			for(int i=0;i<numberOfCorona;i++) {

				if(topCoronaX[i] < -topCoronaVirus.getWidth()) {
					topCoronaX[i] += numberOfCorona * distanceBetweenCorona;
					topCoronaOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 2*minDistBwCorona);

				}
				if(bottomCoronaX[i] < -bottomCoronaVirus.getWidth()) {
					bottomCoronaX[i] += numberOfCorona * distanceBetweenCorona;
					bottomCoronaOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 2*minDistBwCorona);

				}
				if (topCoronaX[i] < Gdx.graphics.getWidth()/4 - topCoronaVirus.getWidth() - 100*scale) {
					topOrBottomflag = -1;
					collidedTopVirusId = -1;
				}
				if (bottomCoronaX[i] < Gdx.graphics.getWidth()/4 - bottomCoronaVirus.getWidth() - 100*scale) {
					topOrBottomflag = -1;
					collidedBottomVirusId = -1;
				}
				topCoronaX[i] -= topCoronaVelocity;
				bottomCoronaX[i] -= bottomCoronaVelocity;
				batch.draw(topCoronaVirus, topCoronaX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + topCoronaOffset[i],topCoronaVirus.getWidth()*(float)scale,topCoronaVirus.getHeight()*(float)scale);
				batch.draw(bottomCoronaVirus, bottomCoronaX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomCoronaVirus.getHeight() + bottomCoronaOffset[i], bottomCoronaVirus.getWidth()*(float)scale,bottomCoronaVirus.getHeight()*(float)scale);

				topCoronaCircles[i].set(topCoronaX[i]+topCoronaVirus.getWidth()/2, Gdx.graphics.getHeight() / 2 + gap / 2 + topCoronaOffset[i] + topCoronaVirus.getHeight()/2,(float)topCoronaVirus.getWidth()*scale/2);
				bottomCoronaCircles[i].set(bottomCoronaX[i]+ bottomCoronaVirus.getWidth()/2, Gdx.graphics.getHeight() / 2 - gap / 2 - bottomCoronaVirus.getHeight() + bottomCoronaOffset[i] + bottomCoronaVirus.getHeight()/2, (float)bottomCoronaVirus.getWidth()*scale/2);
			}

			batch.draw(boy,Gdx.graphics.getWidth()/4 - boy.getWidth()/2, boyY ,(float)boy.getWidth()*scale, (float)boy.getHeight()*scale );
			boyRectangle.set(Gdx.graphics.getWidth()/4 - boy.getWidth()/2, boyY , (float)(boy.getWidth()-40*scale)*scale, (float)boy.getHeight()*scale);

			if(maskX < -mask.getWidth()) {
				int nextMask = randomGenerator.nextInt(5) + 11;
				maskX += nextMask * distanceBetweenCorona;
			}
			maskX -= topCoronaVelocity;
			batch.draw(mask,maskX, Gdx.graphics.getHeight() * randomHeightMask - mask.getHeight()/2, (float)mask.getWidth()*scale, (float)mask.getHeight()*scale );
			maskCircle.set(maskX + mask.getWidth()/2, Gdx.graphics.getHeight() * randomHeightMask , (float)mask.getWidth()*scale/2 );

			if(sanitizerX < -sanitizer.getWidth()) {
				int nextSanitizer = randomGenerator.nextInt(9) + 6;
				sanitizerX += nextSanitizer * distanceBetweenCorona;
			}
			sanitizerX -= topCoronaVelocity;
			batch.draw(sanitizer,sanitizerX, Gdx.graphics.getHeight() * randomHeightSanitizer,(float)sanitizer.getWidth()*scale, (float)sanitizer.getHeight()*scale );
			sanitizerRectangle.set(sanitizerX, Gdx.graphics.getHeight() * randomHeightSanitizer, (float)sanitizer.getWidth()*scale,(float)sanitizer.getHeight()*scale );

			if(socialDistX < -socialDist.getWidth()) {
				int nextSocialDist = randomGenerator.nextInt(13) + 9;
				socialDistX += nextSocialDist * distanceBetweenCorona;
			}
			socialDistX -= topCoronaVelocity;
			batch.draw(socialDist,socialDistX, Gdx.graphics.getHeight() * randomHeightSocialDist - socialDist.getHeight()/2, (float)socialDist.getWidth()*scale, (float)socialDist.getHeight()*scale);
			socialDistCircle.set(socialDistX + socialDist.getWidth()/2, Gdx.graphics.getHeight() * randomHeightSocialDist, (float)socialDist.getHeight()*scale/2 );


			font1.draw(batch, "Score: " + String.valueOf(score), 70*scale,150*scale);
			font.draw(batch, String.valueOf(health), 200*scale,Gdx.graphics.getHeight() - 100*scale);

			if( boyY + boy.getHeight()*scale/2 >0 && boyY + boy.getHeight()*scale/2 < Gdx.graphics.getHeight()  ) {
				velocity += gravity;
				boyY -= velocity;
			} else {
				gameState = 2;
				soundDie.play(1.5f);
			}
			batch.end();

		}else if(gameState == 0) {
			//Gdx.app.log("success",String.valueOf(Gdx.graphics.getWidth() + " and " + String.valueOf(Gdx.graphics.getHeight())));
			batch.draw(startPage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			if(Gdx.input.justTouched()) {
				gameState = 3;
			}
			batch.end();
		} else if(gameState==3) {
			//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.draw(instructions,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			if(Gdx.input.justTouched()) {
				gameState = 4;
			}
			batch.end();
		} else if(gameState == 4) {
			//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.draw(rules,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			if(Gdx.input.justTouched()) {
				gameState = 5;
			}
			batch.end();
		} else if (gameState == 5) {
			stage.clear();
			stageDrawflag = 0;
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.draw(background[0], backgroundX[0], 0 , Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			batch.draw(clouds[0], cloudsX[0], Gdx.graphics.getHeight() - 1500*scale , Gdx.graphics.getWidth(),1500*scale);
			batch.draw(boy,Gdx.graphics.getWidth()/4 - boy.getWidth()/2, boyY ,(float)boy.getWidth()*scale, (float)boy.getHeight()*scale );
			batch.draw(tapToProceed,Gdx.graphics.getWidth()/2 - tapToProceed.getWidth()*scale/2,Gdx.graphics.getHeight()/2 - tapToProceed.getHeight()*scale/2, tapToProceed.getWidth()*scale,tapToProceed.getHeight()*scale);
			if(Gdx.input.justTouched()) {
				velocity -= 20;
				gameState = 1;
			}
			batch.end();
		} else if (gameState == 6) {
			batch.draw(menuScreen, 0, 0 , Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
			batch.end();
			if(stageDrawflag == 0) {
				createStage();
				stageDrawflag = 1;
			}
			stage.act();
			stage.draw();
			acquiredState = 0;
			startGame();
			score = 0;
			topScoringCorona = 0;
			bottomScoringCorona = 0;
			deductedHealth = 1;
			swapTime = 3000;
			boy = boyWithoutMask;
			velocity = 0;
			playOnce = 0;
			health = 100;
			velocityFlag = 0;
			topCoronaVelocity = 8;
			bottomCoronaVelocity = 4;
			tapCount = 0;
			topOrBottomflag = -1;
			collidedTopVirusId = -1;
			collidedBottomVirusId = -1;
			//count=0;
		} else if(gameState == 2) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			if(score > highScore) {
				highScore = score;
			}
			batch.draw(gameover, 0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			font1.draw(batch, String.valueOf(score), Gdx.graphics.getWidth()/2 +  100*scale,Gdx.graphics.getHeight()-97*scale);
			font1.draw(batch, String.valueOf(highScore), Gdx.graphics.getWidth()/2 + 100*scale ,Gdx.graphics.getHeight()-305*scale);
			//update and draw the stage UI
			batch.end();

			if(Gdx.input.justTouched()) {
				tapCount++;
			}
			if(tapCount ==2 )
			{
				gameState = 6;
				health = 100;
			}

		}


		//batch.end();
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(maskCircle.x,maskCircle.y,maskCircle.radius);
		//shapeRenderer.rect(Gdx.graphics.getWidth()/2 - boy.getWidth()/2, boyY , boy.getWidth(), boy.getHeight());
		//shapeRenderer.end();

		detectIntersection();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background[0].dispose();
		background[1].dispose();
		background[2].dispose();

		clouds[0].dispose();
		clouds[1].dispose();
		clouds[2].dispose();

		startPage.dispose();
		instructions.dispose();
		heart.dispose();
		gameover.dispose();

		boy.dispose();
		boyWithoutMask.dispose();
		boyWithMask.dispose();

		mask.dispose();
		sanitizer.dispose();
		socialDist.dispose();
		menuScreen.dispose();

		topCoronaVirus.dispose();
		bottomCoronaVirus.dispose();

		music.dispose();
		//musicFast1.dispose();
		//musicFast2.dispose();
		//musicFast3.dispose();
		jump.dispose();
		collect.dispose();
		soundDie.dispose();
		coronaOverlap.dispose();
	}
}
