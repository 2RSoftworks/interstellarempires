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
package de.r2soft.space.client.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import de.r2soft.space.client.core.ScreenHandler;
import de.r2soft.space.client.screens.SystemScreen;
import de.r2soft.space.client.settings.Resources;
import de.r2soft.space.client.util.ResPack;
import de.r2soft.space.framework.map.IntVec2;
import de.r2soft.space.framework.map.SolarSystem;
import de.r2soft.space.framework.players.Alliance.ALLEGIANCE;
import de.r2soft.space.framework.players.Player;

public class GenericMapTile extends Actor {

	private float posX, posY;
	private float sizeX, sizeY;
	private IntVec2 tileID;
	private ALLEGIANCE ally;
	private ShapeRenderer renderer;
	private SolarSystem childsystem;

	protected IntVec2 id = null;

	/** Player for tile colour */
	private Player claim;

	/**
	 * The constructor will set up the coordinates on which the tile will then be drawn. Also includes
	 * the alliance of the
	 * tile.
	 * 
	 * @param x
	 *          coordinate of the requested tile.
	 * @param y
	 *          coordinate of the requested tile.
	 * @param alliance
	 *          of the tile: player, hostile, neutral and friendly.
	 */
	public GenericMapTile(float x, float y, ALLEGIANCE a, IntVec2 id) {
		posX = x;
		posY = y;
		tileID = id;
		sizeX = sizeY = 100; // Tile size.
		ally = a;
		renderer = new ShapeRenderer();
	}

	/**
	 * Main constructor. Creates tile actor with solar system information. The @Solarsystem object
	 * needs it's claimed
	 * player. Other values will be given along to the child system actor.
	 * 
	 * @param x
	 *          coordinate of the requested tile.
	 * @param y
	 *          coordinate of the requested tile.
	 * @param alliance
	 *          of the tile: player, hostile, neutral and friendly.
	 */
	public GenericMapTile(float x, float y, SolarSystem system, IntVec2 id) {
		posX = x;
		posY = y;
		tileID = id;
		sizeX = sizeY = 100; // Tile size.
		renderer = new ShapeRenderer();
		setupSystem(system);
	}

	private void setupSystem(SolarSystem system) {
		claim = system.getClaim();
		childsystem = system;
		if (claim.equals(null)) {
			ally = ALLEGIANCE.NEUTRAL;
		}
		else {
			ally = claim.equals(Resources.thisPlayer) ? ALLEGIANCE.PLAYER : ALLEGIANCE.HOSTILE;
		}
	}

	public void draw(SpriteBatch batch, float parentAlpha) {

		// Debug frame
		batch.end();
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());
		renderer.translate(getX() / 2, getY() / 2, 0);

		renderer.begin(ShapeType.Rectangle);
		renderer.rect(posX, posY, sizeX, sizeY);
		renderer.end();
		batch.begin();

		switch (ally) {
		case FRIENDLY:
			batch.draw(ResPack.TILE_HEX_FRIEND, posX, posY, 0, 0, sizeX, sizeY, 1, 1, 0);
			break;

		case HOSTILE:
			batch.draw(ResPack.TILE_HEX_ENEMY, posX, posY, 0, 0, sizeX, sizeY, 1, 1, 0);
			break;

		case NEUTRAL:
			batch.draw(ResPack.TILE_HEX_NEUTRAL, posX, posY, 0, 0, sizeX, sizeY, 1, 1, 0);
			break;

		case PLAYER:
			batch.draw(ResPack.TILE_HEX_PLAYER, posX, posY, 0, 0, sizeX, sizeY, 1, 1, 0);
			break;

		default:
			Gdx.app.log(Resources.LOG_HEX_TILE, "fatal error displaying map tile");
			break;
		}
	}

	/**
	 * This will register clicks on the corresponding tile actor.
	 * 
	 * @param x
	 *          position of mouse on screen.
	 * 
	 * @param y
	 *          position of mouse on screen.
	 * 
	 * @param touchable
	 *          Whether the actor allows touch events.
	 * 
	 * @return null
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable) {

		if (touchable && getTouchable() == Touchable.enabled) {
			if (Gdx.input.isTouched(0)) {
				if (x > (this.posX - (this.sizeX)) && x < (this.posX + (this.sizeX))
						&& y > (this.posY - (this.sizeY)) && y < (this.posY + (this.sizeY))) {
					Gdx.app.log(Resources.LOG_HEX_TILE, "you clicked: " + tileID);
					ScreenHandler.getInstance().setScreen(
							new SystemScreen(ScreenHandler.getInstance(), childsystem));
				}
				else {
					// Gdx.app.log(Settings.LOG_HEX_TILE, "nothing there");
				}
			}
		}
		return null;
	}

}
