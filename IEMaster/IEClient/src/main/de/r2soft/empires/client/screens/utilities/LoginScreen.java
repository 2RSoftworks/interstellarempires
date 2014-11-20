/* #########################################################################
 * Copyright (c) 2013 Random Robot Softworks
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ######################################################################### */
package de.r2soft.empires.client.screens.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.r2soft.empires.client.core.GameCore;
import de.r2soft.empires.client.graphics.R2Screen;
import de.r2soft.empires.client.resources.Assets;
import de.r2soft.empires.client.resources.Values;
import de.r2soft.empires.client.screens.gameplay.HexMapScreen;
import de.r2soft.empires.client.screens.overlay.MainMenuOverlay;
import de.r2soft.empires.client.util.Server;

public class LoginScreen extends R2Screen {

  /** UI elements */
  private Stage stage;
  private Table intro, outro, server;
  private TextField userField, passField, serverField;
  private TextButton login, menu, back;
  private CheckBox saveUser, savePw;
  private Preferences prefs;

  /** Background stuff */
  private String name_clear, password_clear;

  public LoginScreen() {
	prefs = Gdx.app.getPreferences(Values.PREFERENCE_FILE_NAME);
	login = new TextButton("LOGIN", Assets.R2_UI_SKIN);
	passField = new TextField("", Assets.R2_UI_SKIN);
	serverField = new TextField("localhost:42002", Assets.R2_UI_SKIN);
	userField = new TextField("", Assets.R2_UI_SKIN);
	saveUser = new CheckBox("Save username?", Assets.R2_UI_SKIN);
	savePw = new CheckBox("Save Password?", Assets.R2_UI_SKIN);
	back = new TextButton("Back to Intro", Assets.R2_UI_SKIN);

	if (prefs.contains(Values.PREFERENCE_SAVE_USERNAME)) {
	  userField.setText(prefs.getString(Values.PREFERENCE_SAVED_USER_NAME));
	  saveUser.setChecked(prefs.getBoolean(Values.PREFERENCE_SAVE_USERNAME));
	}
	else if (prefs.contains(Values.PREFERENCE_SAVE_LOGINPW)) {
	  passField.setText(prefs.getString(Values.PREFERENCE_SAVED_USER_PW));
	  savePw.setChecked(prefs.getBoolean(Values.PREFERENCE_SAVE_USERNAME));
	}
  }

  @Override
  public void show() {

  }

  @Override
  public void resize(int width, int height) {
	if (stage == null)
	  stage = new Stage(new StretchViewport(width, height));
	stage.clear();

	intro = new Table();
	intro.setFillParent(true);

	outro = new Table();
	outro.setFillParent(true);

	server = new Table();
	server.setFillParent(true);

	// Exiting the game
	menu = new TextButton("Main Menu", Assets.R2_UI_SKIN);
	outro.add(menu).width(Values.SIZE_UI_BUTTON_NAVIGON);
	outro.row();
	outro.add(back).width(Values.SIZE_UI_BUTTON_NAVIGON);
	outro.top().left();

	userField.setMessageText(" Your username");
	passField.setMessageText(" Your password");
	passField.setPasswordCharacter('*');
	passField.setPasswordMode(true);

	intro.add(userField).width(Values.SIZE_UI_FIELD_CONTENT);
	intro.row();
	intro.add(passField).width(Values.SIZE_UI_FIELD_CONTENT);
	intro.row();
	intro.add(login).width(Values.SIZE_UI_FIELD_CONTENT);
	intro.row();
	intro.add(saveUser).left();
	intro.row();
	intro.add(savePw).left();
	intro.row();

	/** Populate the server list */
	SelectBox<Server> serverList = new SelectBox<Server>(Assets.R2_UI_SKIN);
	serverList.setItems(fetchServers());

	Label serverTitle = new Label("Select a server:", Assets.R2_UI_SKIN);

	server.add(serverTitle).left();
	server.row();
	server.add(serverList).left();

	server.center().bottom();
	server.setY(Gdx.graphics.getHeight() / 6);

	stage.addActor(outro);
	stage.addActor(intro);
	stage.addActor(server);

  }

  private Server[] fetchServers() {
	Server[] servers = new Server[3];
	servers[0] = new Server("Personal", "localhost", 52666L);
	servers[1] = new Server("Official", "empires.2rsoftworks.de/world1", 52666L);
	servers[2] = new Server("Friends", "ie.delicious.kiwi", 12333L);

	return servers;
  }

  private void setupListeners() {
	login.addListener(new ClickListener() {
	  public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		return true;
	  }

	  public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		scheduleLogin();
	  }
	});

	menu.addListener(new ClickListener() {
	  public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		MainMenuOverlay overlay = new MainMenuOverlay();
		GameCore.getInstance().addOverlay(overlay);

	  }
	});

	back.addListener(new ClickListener() {
	  public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		GameCore.getInstance().setScreen(new IntroductionScreen());
	  }
	});

  }

  public void render(float delta) {
	stage.act(delta);
	stage.draw();

	/* Disables savePW if saveUser is off */
	if (!saveUser.isChecked())
	  savePw.setDisabled(true);
	else
	  savePw.setDisabled(false);

	/* This updates the Preferences DB accordingly */
	if (saveUser.isChecked())
	  prefs.putBoolean(Values.PREFERENCE_SAVE_USERNAME, true);
	else
	  prefs.putBoolean(Values.PREFERENCE_SAVE_USERNAME, false);

	if (savePw.isChecked() && saveUser.isChecked())
	  prefs.putBoolean(Values.PREFERENCE_SAVE_LOGINPW, true);
	else
	  prefs.putBoolean(Values.PREFERENCE_SAVE_LOGINPW, false);

	/** What do we do after we're done in the bathroom? :) */
	prefs.flush();

  }

  private void scheduleLogin() {
	name_clear = userField.getText().toString();
	password_clear = passField.getText().toString();

	// TODO: Properly hash the password, store it in the client hashed and
	// also use the hashed
	// version to send to the server!
	String hash = password_clear;

	if (prefs.getBoolean(Values.PREFERENCE_SAVE_USERNAME))
	  prefs.putString(Values.PREFERENCE_SAVED_USER_NAME, name_clear);

	else if (!prefs.getBoolean(Values.PREFERENCE_SAVE_USERNAME))
	  prefs.putString(Values.PREFERENCE_SAVED_USER_NAME, "");

	if (prefs.getBoolean(Values.PREFERENCE_SAVE_LOGINPW))
	  prefs.putString(Values.PREFERENCE_SAVED_USER_PW, hash);

	else if (!prefs.getBoolean(Values.PREFERENCE_SAVE_LOGINPW))
	  prefs.putString(Values.PREFERENCE_SAVED_USER_PW, "");

	prefs.flush();

	Values.initPlayer(name_clear);

	GameCore.getInstance().setScreen(new HexMapScreen(name_clear));

  }

  @Override
  public void setInputFocus() {
	Gdx.input.setInputProcessor(stage);
	setupListeners();
  }

}
