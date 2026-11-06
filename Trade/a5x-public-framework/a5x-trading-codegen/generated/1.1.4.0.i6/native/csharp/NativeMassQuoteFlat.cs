using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Mass Quote Flat. Mass Quote with the quote entries flattened into numbered fields.
    /// Message Type: m
    /// </summary>
    public class NativeMassQuoteFlat
    {
        /// <summary>Client specified identifier of the message.</summary>
        public string MassQuoteId { get; set; }

        /// <summary>Identity of the matching partition.</summary>
        public byte PartitionId { get; set; }

        /// <summary>0 = Do Not Cancel, 1 = Cancel</summary>
        public byte CancelOnDisconnect { get; set; }

        /// <summary>Whether the quote is anonymous or named. Absence of this field is interpreted as Anonymous (0).</summary>
        public byte Anonymity { get; set; }

        /// <summary>Identifier of the trading account. (Mandatory for Native Gateway Messages).</summary>
        public ulong Account { get; set; }

        /// <summary>Sender Location assigned to the Trader ID/Firm.</summary>
        public string SenderLocation { get; set; }

        /// <summary>Entering Trader ID.</summary>
        public string EnteringTrader { get; set; }

        /// <summary>Client specified identifier of order relevant to self-execution.</summary>
        public string NoTradeKey { get; set; }

        /// <summary>Free format text field. This field may be used to convey client's relevant info. It's always echoed in the reports.</summary>
        public string Memo { get; set; }

        /// <summary>Desk ID through which the order is placed from.</summary>
        public string DeskId { get; set; }

        /// <summary>Number of quote entries in the mass quote (up to 15).</summary>
        public byte TotalQuoteEntries { get; set; }

        /// <summary>Client specified identifier of the quote 1.</summary>
        public string QuoteEntryId1 { get; set; }

        /// <summary>Identifier of the instrument of quote 1.</summary>
        public uint InstrumentId1 { get; set; }

        /// <summary>Bid price of quote 1.</summary>
        public long BidPrice1 { get; set; }

        /// <summary>Bid quantity of quote 1. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize1 { get; set; }

        /// <summary>Offer price of quote 1.</summary>
        public long OfferPrice1 { get; set; }

        /// <summary>Offer quantity of quote 1. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize1 { get; set; }

        /// <summary>Client specified identifier of the quote 2.</summary>
        public string QuoteEntryId2 { get; set; }

        /// <summary>Identifier of the instrument of quote 2.</summary>
        public uint InstrumentId2 { get; set; }

        /// <summary>Bid price of quote 2.</summary>
        public long BidPrice2 { get; set; }

        /// <summary>Bid quantity of quote 2. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize2 { get; set; }

        /// <summary>Offer price of quote 2.</summary>
        public long OfferPrice2 { get; set; }

        /// <summary>Offer quantity of quote 2. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize2 { get; set; }

        /// <summary>Client specified identifier of the quote 3.</summary>
        public string QuoteEntryId3 { get; set; }

        /// <summary>Identifier of the instrument of quote 3.</summary>
        public uint InstrumentId3 { get; set; }

        /// <summary>Bid price of quote 3.</summary>
        public long BidPrice3 { get; set; }

        /// <summary>Bid quantity of quote 3. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize3 { get; set; }

        /// <summary>Offer price of quote 3.</summary>
        public long OfferPrice3 { get; set; }

        /// <summary>Offer quantity of quote 3. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize3 { get; set; }

        /// <summary>Client specified identifier of the quote 4.</summary>
        public string QuoteEntryId4 { get; set; }

        /// <summary>Identifier of the instrument of quote 4.</summary>
        public uint InstrumentId4 { get; set; }

        /// <summary>Bid price of quote 4.</summary>
        public long BidPrice4 { get; set; }

        /// <summary>Bid quantity of quote 4. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize4 { get; set; }

        /// <summary>Offer price of quote 4.</summary>
        public long OfferPrice4 { get; set; }

        /// <summary>Offer quantity of quote 4. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize4 { get; set; }

        /// <summary>Client specified identifier of the quote 5.</summary>
        public string QuoteEntryId5 { get; set; }

        /// <summary>Identifier of the instrument of quote 5.</summary>
        public uint InstrumentId5 { get; set; }

        /// <summary>Bid price of quote 5.</summary>
        public long BidPrice5 { get; set; }

        /// <summary>Bid quantity of quote 5. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize5 { get; set; }

        /// <summary>Offer price of quote 5.</summary>
        public long OfferPrice5 { get; set; }

        /// <summary>Offer quantity of quote 5. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize5 { get; set; }

        /// <summary>Client specified identifier of the quote 6.</summary>
        public string QuoteEntryId6 { get; set; }

        /// <summary>Identifier of the instrument of quote 6.</summary>
        public uint InstrumentId6 { get; set; }

        /// <summary>Bid price of quote 6.</summary>
        public long BidPrice6 { get; set; }

        /// <summary>Bid quantity of quote 6. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize6 { get; set; }

        /// <summary>Offer price of quote 6.</summary>
        public long OfferPrice6 { get; set; }

        /// <summary>Offer quantity of quote 6. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize6 { get; set; }

        /// <summary>Client specified identifier of the quote 7.</summary>
        public string QuoteEntryId7 { get; set; }

        /// <summary>Identifier of the instrument of quote 7.</summary>
        public uint InstrumentId7 { get; set; }

        /// <summary>Bid price of quote 7.</summary>
        public long BidPrice7 { get; set; }

        /// <summary>Bid quantity of quote 7. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize7 { get; set; }

        /// <summary>Offer price of quote 7.</summary>
        public long OfferPrice7 { get; set; }

        /// <summary>Offer quantity of quote 7. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize7 { get; set; }

        /// <summary>Client specified identifier of the quote 8.</summary>
        public string QuoteEntryId8 { get; set; }

        /// <summary>Identifier of the instrument of quote 8.</summary>
        public uint InstrumentId8 { get; set; }

        /// <summary>Bid price of quote 8.</summary>
        public long BidPrice8 { get; set; }

        /// <summary>Bid quantity of quote 8. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize8 { get; set; }

        /// <summary>Offer price of quote 8.</summary>
        public long OfferPrice8 { get; set; }

        /// <summary>Offer quantity of quote 8. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize8 { get; set; }

        /// <summary>Client specified identifier of the quote 9.</summary>
        public string QuoteEntryId9 { get; set; }

        /// <summary>Identifier of the instrument of quote 9.</summary>
        public uint InstrumentId9 { get; set; }

        /// <summary>Bid price of quote 9.</summary>
        public long BidPrice9 { get; set; }

        /// <summary>Bid quantity of quote 9. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize9 { get; set; }

        /// <summary>Offer price of quote 9.</summary>
        public long OfferPrice9 { get; set; }

        /// <summary>Offer quantity of quote 9. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize9 { get; set; }

        /// <summary>Client specified identifier of the quote 10.</summary>
        public string QuoteEntryId10 { get; set; }

        /// <summary>Identifier of the instrument of quote 10.</summary>
        public uint InstrumentId10 { get; set; }

        /// <summary>Bid price of quote 10.</summary>
        public long BidPrice10 { get; set; }

        /// <summary>Bid quantity of quote 10. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize10 { get; set; }

        /// <summary>Offer price of quote 10.</summary>
        public long OfferPrice10 { get; set; }

        /// <summary>Offer quantity of quote10. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize10 { get; set; }

        /// <summary>Client specified identifier of the quote 11.</summary>
        public string QuoteEntryId11 { get; set; }

        /// <summary>Identifier of the instrument of quote 11.</summary>
        public uint InstrumentId11 { get; set; }

        /// <summary>Bid price of quote 11.</summary>
        public long BidPrice11 { get; set; }

        /// <summary>Bid quantity of quote 11. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize11 { get; set; }

        /// <summary>Offer price of quote 11.</summary>
        public long OfferPrice11 { get; set; }

        /// <summary>Offer quantity of quote11. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize11 { get; set; }

        /// <summary>Client specified identifier of the quote 12.</summary>
        public string QuoteEntryId12 { get; set; }

        /// <summary>Identifier of the instrument of quote 12.</summary>
        public uint InstrumentId12 { get; set; }

        /// <summary>Bid price of quote 12.</summary>
        public long BidPrice12 { get; set; }

        /// <summary>Bid quantity of quote 12. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize12 { get; set; }

        /// <summary>Offer price of quote 12.</summary>
        public long OfferPrice12 { get; set; }

        /// <summary>Offer quantity of quote12. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize12 { get; set; }

        /// <summary>Client specified identifier of the quote 13.</summary>
        public string QuoteEntryId13 { get; set; }

        /// <summary>Identifier of the instrument of quote 13.</summary>
        public uint InstrumentId13 { get; set; }

        /// <summary>Bid price of quote 13.</summary>
        public long BidPrice13 { get; set; }

        /// <summary>Bid quantity of quote 13. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize13 { get; set; }

        /// <summary>Offer price of quote 13.</summary>
        public long OfferPrice13 { get; set; }

        /// <summary>Offer quantity of quote13. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize13 { get; set; }

        /// <summary>Client specified identifier of the quote 14.</summary>
        public string QuoteEntryId14 { get; set; }

        /// <summary>Identifier of the instrument of quote 14.</summary>
        public uint InstrumentId14 { get; set; }

        /// <summary>Bid price of quote 14.</summary>
        public long BidPrice14 { get; set; }

        /// <summary>Bid quantity of quote 14. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize14 { get; set; }

        /// <summary>Offer price of quote 14.</summary>
        public long OfferPrice14 { get; set; }

        /// <summary>Offer quantity of quote14. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize14 { get; set; }

        /// <summary>Client specified identifier of the quote 15.</summary>
        public string QuoteEntryId15 { get; set; }

        /// <summary>Identifier of the instrument of quote 15.</summary>
        public uint InstrumentId15 { get; set; }

        /// <summary>Bid price of quote 15.</summary>
        public long BidPrice15 { get; set; }

        /// <summary>Bid quantity of quote 15. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint BidSize15 { get; set; }

        /// <summary>Offer price of quote 15.</summary>
        public long OfferPrice15 { get; set; }

        /// <summary>Offer quantity of quote15. A value of '0' cancels this side of the quote for the given instrument.</summary>
        public uint OfferSize15 { get; set; }

        public static void Parse(NativeMassQuoteFlat msg, Span<byte> data)
        {
            msg.MassQuoteId = Encoding.ASCII.GetString(data.Slice(0, 10)).TrimEnd('\0');
            msg.PartitionId = data[10];
            msg.CancelOnDisconnect = data[11];
            msg.Anonymity = data[12];
            msg.Account = BitConverter.ToUInt64(data.Slice(13, 8));
            msg.SenderLocation = Encoding.ASCII.GetString(data.Slice(21, 10)).TrimEnd('\0');
            msg.EnteringTrader = Encoding.ASCII.GetString(data.Slice(31, 15)).TrimEnd('\0');
            msg.NoTradeKey = Encoding.ASCII.GetString(data.Slice(46, 10)).TrimEnd('\0');
            msg.Memo = Encoding.ASCII.GetString(data.Slice(56, 15)).TrimEnd('\0');
            msg.DeskId = Encoding.ASCII.GetString(data.Slice(71, 15)).TrimEnd('\0');
            msg.TotalQuoteEntries = data[86];
            msg.QuoteEntryId1 = Encoding.ASCII.GetString(data.Slice(87, 10)).TrimEnd('\0');
            msg.InstrumentId1 = BitConverter.ToUInt32(data.Slice(97, 4));
            msg.BidPrice1 = BitConverter.ToInt64(data.Slice(101, 8));
            msg.BidSize1 = BitConverter.ToUInt32(data.Slice(109, 4));
            msg.OfferPrice1 = BitConverter.ToInt64(data.Slice(113, 8));
            msg.OfferSize1 = BitConverter.ToUInt32(data.Slice(121, 4));
            msg.QuoteEntryId2 = Encoding.ASCII.GetString(data.Slice(125, 10)).TrimEnd('\0');
            msg.InstrumentId2 = BitConverter.ToUInt32(data.Slice(135, 4));
            msg.BidPrice2 = BitConverter.ToInt64(data.Slice(139, 8));
            msg.BidSize2 = BitConverter.ToUInt32(data.Slice(147, 4));
            msg.OfferPrice2 = BitConverter.ToInt64(data.Slice(151, 8));
            msg.OfferSize2 = BitConverter.ToUInt32(data.Slice(159, 4));
            msg.QuoteEntryId3 = Encoding.ASCII.GetString(data.Slice(163, 10)).TrimEnd('\0');
            msg.InstrumentId3 = BitConverter.ToUInt32(data.Slice(173, 4));
            msg.BidPrice3 = BitConverter.ToInt64(data.Slice(177, 8));
            msg.BidSize3 = BitConverter.ToUInt32(data.Slice(185, 4));
            msg.OfferPrice3 = BitConverter.ToInt64(data.Slice(189, 8));
            msg.OfferSize3 = BitConverter.ToUInt32(data.Slice(197, 4));
            msg.QuoteEntryId4 = Encoding.ASCII.GetString(data.Slice(201, 10)).TrimEnd('\0');
            msg.InstrumentId4 = BitConverter.ToUInt32(data.Slice(211, 4));
            msg.BidPrice4 = BitConverter.ToInt64(data.Slice(215, 8));
            msg.BidSize4 = BitConverter.ToUInt32(data.Slice(223, 4));
            msg.OfferPrice4 = BitConverter.ToInt64(data.Slice(227, 8));
            msg.OfferSize4 = BitConverter.ToUInt32(data.Slice(235, 4));
            msg.QuoteEntryId5 = Encoding.ASCII.GetString(data.Slice(239, 10)).TrimEnd('\0');
            msg.InstrumentId5 = BitConverter.ToUInt32(data.Slice(249, 4));
            msg.BidPrice5 = BitConverter.ToInt64(data.Slice(253, 8));
            msg.BidSize5 = BitConverter.ToUInt32(data.Slice(261, 4));
            msg.OfferPrice5 = BitConverter.ToInt64(data.Slice(265, 8));
            msg.OfferSize5 = BitConverter.ToUInt32(data.Slice(273, 4));
            msg.QuoteEntryId6 = Encoding.ASCII.GetString(data.Slice(277, 10)).TrimEnd('\0');
            msg.InstrumentId6 = BitConverter.ToUInt32(data.Slice(287, 4));
            msg.BidPrice6 = BitConverter.ToInt64(data.Slice(291, 8));
            msg.BidSize6 = BitConverter.ToUInt32(data.Slice(299, 4));
            msg.OfferPrice6 = BitConverter.ToInt64(data.Slice(303, 8));
            msg.OfferSize6 = BitConverter.ToUInt32(data.Slice(311, 4));
            msg.QuoteEntryId7 = Encoding.ASCII.GetString(data.Slice(315, 10)).TrimEnd('\0');
            msg.InstrumentId7 = BitConverter.ToUInt32(data.Slice(325, 4));
            msg.BidPrice7 = BitConverter.ToInt64(data.Slice(329, 8));
            msg.BidSize7 = BitConverter.ToUInt32(data.Slice(337, 4));
            msg.OfferPrice7 = BitConverter.ToInt64(data.Slice(341, 8));
            msg.OfferSize7 = BitConverter.ToUInt32(data.Slice(349, 4));
            msg.QuoteEntryId8 = Encoding.ASCII.GetString(data.Slice(353, 10)).TrimEnd('\0');
            msg.InstrumentId8 = BitConverter.ToUInt32(data.Slice(363, 4));
            msg.BidPrice8 = BitConverter.ToInt64(data.Slice(367, 8));
            msg.BidSize8 = BitConverter.ToUInt32(data.Slice(375, 4));
            msg.OfferPrice8 = BitConverter.ToInt64(data.Slice(379, 8));
            msg.OfferSize8 = BitConverter.ToUInt32(data.Slice(387, 4));
            msg.QuoteEntryId9 = Encoding.ASCII.GetString(data.Slice(391, 10)).TrimEnd('\0');
            msg.InstrumentId9 = BitConverter.ToUInt32(data.Slice(401, 4));
            msg.BidPrice9 = BitConverter.ToInt64(data.Slice(405, 8));
            msg.BidSize9 = BitConverter.ToUInt32(data.Slice(413, 4));
            msg.OfferPrice9 = BitConverter.ToInt64(data.Slice(417, 8));
            msg.OfferSize9 = BitConverter.ToUInt32(data.Slice(425, 4));
            msg.QuoteEntryId10 = Encoding.ASCII.GetString(data.Slice(429, 10)).TrimEnd('\0');
            msg.InstrumentId10 = BitConverter.ToUInt32(data.Slice(439, 4));
            msg.BidPrice10 = BitConverter.ToInt64(data.Slice(443, 8));
            msg.BidSize10 = BitConverter.ToUInt32(data.Slice(451, 4));
            msg.OfferPrice10 = BitConverter.ToInt64(data.Slice(455, 8));
            msg.OfferSize10 = BitConverter.ToUInt32(data.Slice(463, 4));
            msg.QuoteEntryId11 = Encoding.ASCII.GetString(data.Slice(467, 10)).TrimEnd('\0');
            msg.InstrumentId11 = BitConverter.ToUInt32(data.Slice(477, 4));
            msg.BidPrice11 = BitConverter.ToInt64(data.Slice(481, 8));
            msg.BidSize11 = BitConverter.ToUInt32(data.Slice(489, 4));
            msg.OfferPrice11 = BitConverter.ToInt64(data.Slice(493, 8));
            msg.OfferSize11 = BitConverter.ToUInt32(data.Slice(501, 4));
            msg.QuoteEntryId12 = Encoding.ASCII.GetString(data.Slice(505, 10)).TrimEnd('\0');
            msg.InstrumentId12 = BitConverter.ToUInt32(data.Slice(515, 4));
            msg.BidPrice12 = BitConverter.ToInt64(data.Slice(519, 8));
            msg.BidSize12 = BitConverter.ToUInt32(data.Slice(527, 4));
            msg.OfferPrice12 = BitConverter.ToInt64(data.Slice(531, 8));
            msg.OfferSize12 = BitConverter.ToUInt32(data.Slice(539, 4));
            msg.QuoteEntryId13 = Encoding.ASCII.GetString(data.Slice(543, 10)).TrimEnd('\0');
            msg.InstrumentId13 = BitConverter.ToUInt32(data.Slice(553, 4));
            msg.BidPrice13 = BitConverter.ToInt64(data.Slice(557, 8));
            msg.BidSize13 = BitConverter.ToUInt32(data.Slice(565, 4));
            msg.OfferPrice13 = BitConverter.ToInt64(data.Slice(569, 8));
            msg.OfferSize13 = BitConverter.ToUInt32(data.Slice(577, 4));
            msg.QuoteEntryId14 = Encoding.ASCII.GetString(data.Slice(581, 10)).TrimEnd('\0');
            msg.InstrumentId14 = BitConverter.ToUInt32(data.Slice(591, 4));
            msg.BidPrice14 = BitConverter.ToInt64(data.Slice(595, 8));
            msg.BidSize14 = BitConverter.ToUInt32(data.Slice(603, 4));
            msg.OfferPrice14 = BitConverter.ToInt64(data.Slice(607, 8));
            msg.OfferSize14 = BitConverter.ToUInt32(data.Slice(615, 4));
            msg.QuoteEntryId15 = Encoding.ASCII.GetString(data.Slice(619, 10)).TrimEnd('\0');
            msg.InstrumentId15 = BitConverter.ToUInt32(data.Slice(629, 4));
            msg.BidPrice15 = BitConverter.ToInt64(data.Slice(633, 8));
            msg.BidSize15 = BitConverter.ToUInt32(data.Slice(641, 4));
            msg.OfferPrice15 = BitConverter.ToInt64(data.Slice(645, 8));
            msg.OfferSize15 = BitConverter.ToUInt32(data.Slice(653, 4));
        }

        public static void Encode(NativeMassQuoteFlat msg, Span<byte> data)
        {
            Encoding.ASCII.GetBytes((msg.MassQuoteId ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(0, 10));
            data[10] = msg.PartitionId;
            data[11] = msg.CancelOnDisconnect;
            data[12] = msg.Anonymity;
            BitConverter.TryWriteBytes(data.Slice(13, 8), msg.Account);
            Encoding.ASCII.GetBytes((msg.SenderLocation ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(21, 10));
            Encoding.ASCII.GetBytes((msg.EnteringTrader ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(31, 15));
            Encoding.ASCII.GetBytes((msg.NoTradeKey ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(46, 10));
            Encoding.ASCII.GetBytes((msg.Memo ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(56, 15));
            Encoding.ASCII.GetBytes((msg.DeskId ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(71, 15));
            data[86] = msg.TotalQuoteEntries;
            Encoding.ASCII.GetBytes((msg.QuoteEntryId1 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(87, 10));
            BitConverter.TryWriteBytes(data.Slice(97, 4), msg.InstrumentId1);
            BitConverter.TryWriteBytes(data.Slice(101, 8), msg.BidPrice1);
            BitConverter.TryWriteBytes(data.Slice(109, 4), msg.BidSize1);
            BitConverter.TryWriteBytes(data.Slice(113, 8), msg.OfferPrice1);
            BitConverter.TryWriteBytes(data.Slice(121, 4), msg.OfferSize1);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId2 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(125, 10));
            BitConverter.TryWriteBytes(data.Slice(135, 4), msg.InstrumentId2);
            BitConverter.TryWriteBytes(data.Slice(139, 8), msg.BidPrice2);
            BitConverter.TryWriteBytes(data.Slice(147, 4), msg.BidSize2);
            BitConverter.TryWriteBytes(data.Slice(151, 8), msg.OfferPrice2);
            BitConverter.TryWriteBytes(data.Slice(159, 4), msg.OfferSize2);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId3 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(163, 10));
            BitConverter.TryWriteBytes(data.Slice(173, 4), msg.InstrumentId3);
            BitConverter.TryWriteBytes(data.Slice(177, 8), msg.BidPrice3);
            BitConverter.TryWriteBytes(data.Slice(185, 4), msg.BidSize3);
            BitConverter.TryWriteBytes(data.Slice(189, 8), msg.OfferPrice3);
            BitConverter.TryWriteBytes(data.Slice(197, 4), msg.OfferSize3);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId4 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(201, 10));
            BitConverter.TryWriteBytes(data.Slice(211, 4), msg.InstrumentId4);
            BitConverter.TryWriteBytes(data.Slice(215, 8), msg.BidPrice4);
            BitConverter.TryWriteBytes(data.Slice(223, 4), msg.BidSize4);
            BitConverter.TryWriteBytes(data.Slice(227, 8), msg.OfferPrice4);
            BitConverter.TryWriteBytes(data.Slice(235, 4), msg.OfferSize4);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId5 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(239, 10));
            BitConverter.TryWriteBytes(data.Slice(249, 4), msg.InstrumentId5);
            BitConverter.TryWriteBytes(data.Slice(253, 8), msg.BidPrice5);
            BitConverter.TryWriteBytes(data.Slice(261, 4), msg.BidSize5);
            BitConverter.TryWriteBytes(data.Slice(265, 8), msg.OfferPrice5);
            BitConverter.TryWriteBytes(data.Slice(273, 4), msg.OfferSize5);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId6 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(277, 10));
            BitConverter.TryWriteBytes(data.Slice(287, 4), msg.InstrumentId6);
            BitConverter.TryWriteBytes(data.Slice(291, 8), msg.BidPrice6);
            BitConverter.TryWriteBytes(data.Slice(299, 4), msg.BidSize6);
            BitConverter.TryWriteBytes(data.Slice(303, 8), msg.OfferPrice6);
            BitConverter.TryWriteBytes(data.Slice(311, 4), msg.OfferSize6);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId7 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(315, 10));
            BitConverter.TryWriteBytes(data.Slice(325, 4), msg.InstrumentId7);
            BitConverter.TryWriteBytes(data.Slice(329, 8), msg.BidPrice7);
            BitConverter.TryWriteBytes(data.Slice(337, 4), msg.BidSize7);
            BitConverter.TryWriteBytes(data.Slice(341, 8), msg.OfferPrice7);
            BitConverter.TryWriteBytes(data.Slice(349, 4), msg.OfferSize7);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId8 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(353, 10));
            BitConverter.TryWriteBytes(data.Slice(363, 4), msg.InstrumentId8);
            BitConverter.TryWriteBytes(data.Slice(367, 8), msg.BidPrice8);
            BitConverter.TryWriteBytes(data.Slice(375, 4), msg.BidSize8);
            BitConverter.TryWriteBytes(data.Slice(379, 8), msg.OfferPrice8);
            BitConverter.TryWriteBytes(data.Slice(387, 4), msg.OfferSize8);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId9 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(391, 10));
            BitConverter.TryWriteBytes(data.Slice(401, 4), msg.InstrumentId9);
            BitConverter.TryWriteBytes(data.Slice(405, 8), msg.BidPrice9);
            BitConverter.TryWriteBytes(data.Slice(413, 4), msg.BidSize9);
            BitConverter.TryWriteBytes(data.Slice(417, 8), msg.OfferPrice9);
            BitConverter.TryWriteBytes(data.Slice(425, 4), msg.OfferSize9);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId10 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(429, 10));
            BitConverter.TryWriteBytes(data.Slice(439, 4), msg.InstrumentId10);
            BitConverter.TryWriteBytes(data.Slice(443, 8), msg.BidPrice10);
            BitConverter.TryWriteBytes(data.Slice(451, 4), msg.BidSize10);
            BitConverter.TryWriteBytes(data.Slice(455, 8), msg.OfferPrice10);
            BitConverter.TryWriteBytes(data.Slice(463, 4), msg.OfferSize10);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId11 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(467, 10));
            BitConverter.TryWriteBytes(data.Slice(477, 4), msg.InstrumentId11);
            BitConverter.TryWriteBytes(data.Slice(481, 8), msg.BidPrice11);
            BitConverter.TryWriteBytes(data.Slice(489, 4), msg.BidSize11);
            BitConverter.TryWriteBytes(data.Slice(493, 8), msg.OfferPrice11);
            BitConverter.TryWriteBytes(data.Slice(501, 4), msg.OfferSize11);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId12 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(505, 10));
            BitConverter.TryWriteBytes(data.Slice(515, 4), msg.InstrumentId12);
            BitConverter.TryWriteBytes(data.Slice(519, 8), msg.BidPrice12);
            BitConverter.TryWriteBytes(data.Slice(527, 4), msg.BidSize12);
            BitConverter.TryWriteBytes(data.Slice(531, 8), msg.OfferPrice12);
            BitConverter.TryWriteBytes(data.Slice(539, 4), msg.OfferSize12);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId13 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(543, 10));
            BitConverter.TryWriteBytes(data.Slice(553, 4), msg.InstrumentId13);
            BitConverter.TryWriteBytes(data.Slice(557, 8), msg.BidPrice13);
            BitConverter.TryWriteBytes(data.Slice(565, 4), msg.BidSize13);
            BitConverter.TryWriteBytes(data.Slice(569, 8), msg.OfferPrice13);
            BitConverter.TryWriteBytes(data.Slice(577, 4), msg.OfferSize13);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId14 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(581, 10));
            BitConverter.TryWriteBytes(data.Slice(591, 4), msg.InstrumentId14);
            BitConverter.TryWriteBytes(data.Slice(595, 8), msg.BidPrice14);
            BitConverter.TryWriteBytes(data.Slice(603, 4), msg.BidSize14);
            BitConverter.TryWriteBytes(data.Slice(607, 8), msg.OfferPrice14);
            BitConverter.TryWriteBytes(data.Slice(615, 4), msg.OfferSize14);
            Encoding.ASCII.GetBytes((msg.QuoteEntryId15 ?? string.Empty).PadRight(10, '\0')).AsSpan(0, 10).CopyTo(data.Slice(619, 10));
            BitConverter.TryWriteBytes(data.Slice(629, 4), msg.InstrumentId15);
            BitConverter.TryWriteBytes(data.Slice(633, 8), msg.BidPrice15);
            BitConverter.TryWriteBytes(data.Slice(641, 4), msg.BidSize15);
            BitConverter.TryWriteBytes(data.Slice(645, 8), msg.OfferPrice15);
            BitConverter.TryWriteBytes(data.Slice(653, 4), msg.OfferSize15);
        }
    }
}
