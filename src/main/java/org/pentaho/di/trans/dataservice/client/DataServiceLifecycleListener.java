/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.dataservice.client;

import org.pentaho.di.core.annotations.LifecyclePlugin;
import org.pentaho.di.core.lifecycle.LifeEventHandler;
import org.pentaho.di.core.lifecycle.LifecycleException;
import org.pentaho.di.core.lifecycle.LifecycleListener;
import org.pentaho.di.trans.dataservice.client.api.DataServiceClientService;
import org.pentaho.di.trans.dataservice.jdbc.ThinConnection;
import org.pentaho.di.ui.spoon.Spoon;

@LifecyclePlugin( id = "DataServiceLifecycleListener" )
public class DataServiceLifecycleListener implements LifecycleListener {

  private Spoon spoon = null;
  private DataServiceClientService dataServiceClientService = null;

  public void bind( DataServiceClientService service ) {
    dataServiceClientService = service;
    if ( spoon != null ) {
      setup();
    }
  }

  public void unbind( DataServiceClientService service ) {
    ThinConnection.localClient = null;
  }

  @Override public void onStart( LifeEventHandler handler ) throws LifecycleException {
    spoon = Spoon.getInstance();
    if ( dataServiceClientService != null ) {
      setup();
    }
  }

  @Override public void onExit( LifeEventHandler handler ) throws LifecycleException {

  }

  private void setup() {
    dataServiceClientService.setRepository( spoon.getRepository() );
    dataServiceClientService.setMetaStore( spoon.getMetaStore() );
    ThinConnection.localClient = dataServiceClientService;
  }
}