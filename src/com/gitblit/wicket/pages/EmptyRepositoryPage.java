/*
 * Copyright 2011 gitblit.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitblit.wicket.pages;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

import com.gitblit.Constants;
import com.gitblit.GitBlit;
import com.gitblit.Keys;
import com.gitblit.wicket.WicketUtils;
import com.gitblit.wicket.panels.RepositoryUrlPanel;

public class EmptyRepositoryPage extends RootPage {

	public EmptyRepositoryPage(PageParameters params) {
		super(params);

		String repositoryName = WicketUtils.getRepositoryName(params);
		setupPage(repositoryName, getString("gb.emptyRepository"));

		List<String> repositoryUrls = new ArrayList<String>();

		if (GitBlit.getBoolean(Keys.git.enableGitServlet, true)) {
			StringBuilder sb = new StringBuilder();
			sb.append(WicketUtils.getGitblitURL(getRequestCycle().getRequest()));
			sb.append(Constants.GIT_PATH);
			sb.append(repositoryName);
			repositoryUrls.add(sb.toString());
		}
		repositoryUrls.addAll(GitBlit.self().getOtherCloneUrls(repositoryName));
		
		add(new Label("repository", repositoryName));
		add(new RepositoryUrlPanel("pushurl", repositoryUrls.get(0)));
		add(new Label("syntax", MessageFormat.format("git remote add gitblit {0}\ngit push gitblit master", repositoryUrls.get(0))));
	}
}