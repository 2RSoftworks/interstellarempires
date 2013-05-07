/* 
 * Copyright (c) 2012 Leander Sabel
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

package de.r2soft.space.framework.map;

import com.badlogic.gdx.math.Vector2;

public class Location {

	private Vector2 pos;

	public Location(Vector2 pos) {
		this.pos = pos;
	}

	public float getX() {
		return pos.x;
	}

	public float getY() {
		return pos.y;
	}

}
