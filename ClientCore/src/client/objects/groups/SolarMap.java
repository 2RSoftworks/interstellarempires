/* 
 * Copyright (c) 2013 ***REMOVED***
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
 */
package client.objects.groups;

import java.util.Set;

import client.settings.Settings;
import client.types.IntVec2;
import client.util.Find;
import client.util.ResourcePacker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Disposable;

import framework.map.SolarSystem;
import framework.objects.GameObject;
import framework.objects.Star.StarType;

/**
 * Counterpart to the @HexMap. Will display a solarsystem to the player. Extending the Group instead of the Actor to hold own
 * Actor instances
 * 
 * @author ***REMOVED***
 * 
 */
public class SolarMap extends Group implements Disposable {

	private IntVec2 tileID;
	private SolarSystem system;
	private ShapeRenderer renderer;
	private int offset;
	private StarType starType;
	private ResourcePacker res;

		/**
		 * Stuff that needs to be done no matter what constructor is called ^_^
		 */
		{
			offset = 100;
			renderer = new ShapeRenderer();
			res = new ResourcePacker();
			res.loadTextures();
		}

	/**
	 * 
	 * @param TileID
	 *         The id of the tile in the map as a 2-dimensional vector
	 * @param system
	 *         The absolute solarsystem data from the framework.
	 */
	public SolarMap(IntVec2 tileID, SolarSystem solar) {
		this.tileID = tileID;
		if (solar != null)
			{
				system = solar;
				starType = solar.getStar().getType();

			} else
			{
				Gdx.app.log(Settings.LOG, "FATAL ERROR RECEIVING MAP INFORMATION: " + tileID);
			}
	}

	public SolarMap(IntVec2 tileID) {
		this.tileID = tileID;
	}

	public void draw(SpriteBatch batch, float parentAlpha) {

		batch.end();
		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());
		renderer.translate(getX() / 2, getY() / 2, 0);

		renderer.begin(ShapeType.Circle);

		renderer.circle((Gdx.graphics.getWidth() / 2) - offset, Gdx.graphics.getHeight() / 2, system.getRadius());
		renderer.end();
		batch.begin();

		switch (starType) {
			case BROWNDWARF:
				batch.draw(res.getStarBrownDwarf(), Find.getCenter().x - 20 - offset, Find.getCenter().y - 20, 0, 0, 40, 40, 1, 1, 0);
				break;

			case BLUEGIANT:
				batch.draw(res.getStarNeutron(), Find.getCenter().x - 50 - offset, Find.getCenter().y - 50, 0, 0, 100, 100, 1, 1, 0);
				break;

			case NEUTRON:
				batch.draw(res.getStarNeutron(), Find.getCenter().x - 12.5f - offset, Find.getCenter().y - 12.5f, 0, 0, 25, 25, 1, 1, 0);
				break;

			case REDDWARF:
				batch.draw(res.getStarRedDwarf(), Find.getCenter().x - 25 - offset, Find.getCenter().y - 25, 0, 0, 128, 128, 1, 1, 0);
				break;

			case REDGIANT:
				batch.draw(res.getStarRedDwarf(), Find.getCenter().x - 25 - offset, Find.getCenter().y - 25, 0, 0, 128, 128, 1, 1, 0);
				break;

			default:
				break;
		}
	}

	/**
	 * Will be called to update the map view with current data.
	 * 
	 * @param o
	 *         A set of all @GameObject instances in the system.
	 */
	public void updateData(Set<GameObject> o) {

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
