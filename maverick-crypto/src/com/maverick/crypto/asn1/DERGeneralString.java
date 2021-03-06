
				/*
 *  Adito
 *
 *  Copyright (C) 2003-2006 3SP LTD. All Rights Reserved
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
			
package com.maverick.crypto.asn1;

import java.io.IOException;

public class DERGeneralString
	extends DERObject implements DERString
{
	private String string;

	public static DERGeneralString getInstance(
		Object obj)
	{
		if (obj == null || obj instanceof DERGeneralString)
		{
			return (DERGeneralString) obj;
		}
		if (obj instanceof ASN1OctetString)
		{
			return new DERGeneralString(((ASN1OctetString) obj).getOctets());
		}
		if (obj instanceof ASN1TaggedObject)
		{
			return getInstance(((ASN1TaggedObject) obj).getObject());
		}
		throw new IllegalArgumentException("illegal object in getInstance: "
				+ obj.getClass().getName());
	}

	public static DERGeneralString getInstance(
		ASN1TaggedObject obj,
		boolean explicit)
	{
		return getInstance(obj.getObject());
	}

	public DERGeneralString(byte[] string)
	{
		char[] cs = new char[string.length];
		for (int i = 0; i != cs.length; i++) {
			cs[i] = (char) (string[i] & 0xff);
		}
		this.string = new String(cs);
	}

	public DERGeneralString(String string)
	{
		this.string = string;
	}

	public String getString()
	{
		return string;
	}

	public byte[] getOctets()
	{
		char[] cs = string.toCharArray();
		byte[] bs = new byte[cs.length];
		for (int i = 0; i != cs.length; i++)
		{
			bs[i] = (byte) cs[i];
		}
		return bs;
	}

	void encode(DEROutputStream out)
		throws IOException
	{
		out.writeEncoded(GENERAL_STRING, this.getOctets());
	}

	public int hashCode()
	{
		return this.getString().hashCode();
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof DERGeneralString))
		{
			return false;
		}
		DERGeneralString s = (DERGeneralString) o;
		return this.getString().equals(s.getString());
	}
}
