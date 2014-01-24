/* 
 * Copyright (c) 2013 Leander Sabel
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

package de.r2soft.space.server.ws.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.namespace.QName;

@WebService(targetNamespace = "http://2rSoftworks.de/")
public interface ConnectionService {
  
  public static QName ServiceName = new QName("http://2rSoftworks.de/", "ConnectionService");

  @WebMethod
  public Integer connect(String username, String password);

  @WebMethod
  public boolean disconnect(Integer sessionID);
  

}
