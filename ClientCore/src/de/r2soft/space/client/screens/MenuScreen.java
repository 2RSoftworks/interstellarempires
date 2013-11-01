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
package de.r2soft.space.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import de.r2soft.space.client.core.ScreenHandler;
import de.r2soft.space.client.groups.HexMap;
import de.r2soft.space.client.screens.utilities.LoginScreen;
import de.r2soft.space.client.screens.utilities.SettingsScreen;
import de.r2soft.space.client.settings.Resources;
import de.r2soft.space.client.util.ResPack;
import de.r2soft.space.client.util.Sizes;

/**
 * For now the only menu screen with buttons and a scrollable map view.
 * Hopefully :)
 * 
 * @author: ***REMOVED***
 */
@Deprecated
public class MenuScreen implements Screen {

	/** Container and Backends */
	private ScreenHandler handler;
	private Stage stage;
	private TextButton settings;
	private TextButton exitGame;
	private Table mapTable;

	private HexMap map;

	public MenuScreen(ScreenHandler handler) {
		this.handler = handler;
		Gdx.graphics
				.setTitle(Resources.SUPERTITLE + " - "
						+ Resources.VERSION_NUMBER + " - "
						+ Resources.SCREENTITLE_HOME);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		if (Gdx.input.getX() > 120 && Gdx.input.getX() < 720
				&& Gdx.input.getY() > 100 && Gdx.input.getY() < 450) {
			map.setInputToChild();
		} else {
			Gdx.input.setInputProcessor(stage);
		}

	}

	@Override
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();

		Table table = new Table();
		mapTable = new Table();

		Table backToIntro = new Table();
		backToIntro.setFillParent(true);
		stage.addActor(backToIntro);
		TextButton backham = new TextButton("Logout", ResPack.UI_SKIN);
		backham.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				handler.setScreen(new LoginScreen(handler));
				// TODO: server logout?
			}
		});

		backToIntro.add(backham).width(Sizes.SIZE_UI_BUTTON_NAVIGON);
		backToIntro.row();
		backToIntro.top().left();

		table.setFillParent(true);
		mapTable.setFillParent(true);
		stage.addActor(table);
		stage.addActor(mapTable);

		map = new HexMap(600f, 350f, handler);

		mapTable.add(map);
		mapTable.center(); // First center it
		mapTable.setX(-300); // Then reduce the X value /**/ TODO:
								// Gdx.graphics.getWidth() / 3

		settings = new TextButton("Settings", ResPack.UI_SKIN);
		exitGame = new TextButton("Logout & Quit", ResPack.UI_SKIN);

		table.add(settings).width(Sizes.SIZE_UI_BUTTON_NAVIGON);
		table.row();
		table.add(exitGame).width(Sizes.SIZE_UI_BUTTON_NAVIGON);
		table.top().right();

		/** OnClickListener */

		settings.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				handler.setScreen(new SettingsScreen(handler));
			}
		});

		exitGame.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
			}
		});

	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		map.dispose();
		stage.dispose();
	}

}
