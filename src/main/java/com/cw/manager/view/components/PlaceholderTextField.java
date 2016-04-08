package com.cw.manager.view.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {
	private String placeholder;
	
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		
		if (placeholder.length() == 0 || getText().length() > 0) {
			return;
		}
		
		 final Graphics2D _g = (Graphics2D) g;
	        _g.setRenderingHint(
	            RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
	        _g.setColor(getDisabledTextColor());
	        _g.drawString(placeholder, getInsets().left, g.getFontMetrics()
	            .getMaxAscent() + getInsets().top);
	}
	
	public void setPlaceholder(final String s) {
		placeholder = s;
	}

}
