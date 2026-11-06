using System;
using System.Buffers.Binary;
using System.Text;

namespace A5X.Trading.Mitch.Model
{
    /// <summary>
    /// Sent during recovery to provide historical trade information
    /// </summary>
    public class MitchRecoveryTrade
    {
        /// <summary>
        /// Nanoseconds offset from the last Time message
        /// </summary>
        public uint Nanosecond { get; set; }

        /// <summary>
        /// Identifier for the order
        /// </summary>
        public ulong OrderId { get; set; }

        /// <summary>
        /// Quantity executed
        /// </summary>
        public uint ExecutedQuantity { get; set; }

        /// <summary>
        /// Executed price
        /// </summary>
        public long Price { get; set; }

        public decimal PriceAsDecimal => Price / 100000000m;

        /// <summary>
        /// Numeric identifier of the instrument
        /// </summary>
        public uint InstrumentId { get; set; }

        /// <summary>
        /// Unique identifier of the trade
        /// </summary>
        public ulong TradeId { get; set; }

        /// <summary>
        /// Reserved for future use.
        /// </summary>
        public long Reserved1 { get; set; }

        public decimal Reserved1AsDecimal => Reserved1 / 100000000m;

        /// <summary>
        /// Reserved for future use.
        /// </summary>
        public long Reserved2 { get; set; }

        public decimal Reserved2AsDecimal => Reserved2 / 100000000m;

        /// <summary>
        /// Reserved for future use.
        /// </summary>
        public long Reserved3 { get; set; }

        public decimal Reserved3AsDecimal => Reserved3 / 100000000m;

        /// <summary>
        /// Date and time when the trade was executed [Epoch]
        /// </summary>
        public ulong TradingDateTime { get; set; }

        /// <summary>
        /// Instrument identification code
        /// </summary>
        public string InstrumentIdentificationCode { get; set; }

        /// <summary>
        /// 1 = ISIN Code
        /// </summary>
        public byte InstrumentIdentificationCodeType { get; set; }

        /// <summary>
        /// Currency in which the price of the trade is expressed
        /// </summary>
        public string Currency { get; set; }

        /// <summary>
        /// Identification of the venue where the trade was executed
        /// </summary>
        public string VenueOfExecution { get; set; }

        /// <summary>
        /// 1 = MONE - Monetary value, 2 = PERC - Percentage, 3 = YIEL - Yield
        /// </summary>
        public byte PriceNotation { get; set; }

        /// <summary>
        /// Notional value relevant to the security
        /// </summary>
        public long NotionalAmount { get; set; }

        public decimal NotionalAmountAsDecimal => NotionalAmount / 100000000m;

        /// <summary>
        /// Reserved for future use.
        /// </summary>
        public string Reserved4 { get; set; }

        /// <summary>
        /// Date and time when the trade was published by the trading venue [Epoch]
        /// </summary>
        public ulong PublicationDateTime { get; set; }

        /// <summary>
        /// Buyer Firm ID
        /// </summary>
        public string BuyerFirm { get; set; }

        /// <summary>
        /// Seller Firm ID
        /// </summary>
        public string SellerFirm { get; set; }

        /// <summary>
        /// PTCancellationFlag. Indicates if the trade represented by the message has been cancelled (Value = CANC)
        /// </summary>
        public string CancellationFlag { get; set; }

        /// <summary>
        /// PTAmendmentFlag. Indicates if the trade represented by the message has been amended (Value = AMND)
        /// </summary>
        public string AmendmentFlag { get; set; }

        /// <summary>
        /// 1 = Regular, 11 = Negotiated Trades
        /// </summary>
        public byte SubBook { get; set; }

        /// <summary>
        /// Bit 0: Trade Condition Flag, Bit 1: Internal Crossed Trade, Bit 5: RLP
        /// </summary>
        public byte Flags { get; set; }

        /// <summary>
        /// A = Re-Opening Auction, C = Closing Auction, O = Opening Auction
        /// </summary>
        public byte AuctionType { get; set; }

        public static void Parse(MitchRecoveryTrade msg, Span<byte> data)
        {
            int offset = 0;
            msg.Nanosecond = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.OrderId = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            offset += 8;
            msg.ExecutedQuantity = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            ulong priceRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.Price = (priceRaw >> 63) != 0 ? -(long)(priceRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)priceRaw;
            offset += 8;
            msg.InstrumentId = BinaryPrimitives.ReadUInt32LittleEndian(data.Slice(offset, 4));
            offset += 4;
            msg.TradeId = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            offset += 8;
            ulong reserved1Raw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.Reserved1 = (reserved1Raw >> 63) != 0 ? -(long)(reserved1Raw & 0x7FFFFFFFFFFFFFFFUL) : (long)reserved1Raw;
            offset += 8;
            ulong reserved2Raw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.Reserved2 = (reserved2Raw >> 63) != 0 ? -(long)(reserved2Raw & 0x7FFFFFFFFFFFFFFFUL) : (long)reserved2Raw;
            offset += 8;
            ulong reserved3Raw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.Reserved3 = (reserved3Raw >> 63) != 0 ? -(long)(reserved3Raw & 0x7FFFFFFFFFFFFFFFUL) : (long)reserved3Raw;
            offset += 8;
            msg.TradingDateTime = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            offset += 8;
            msg.InstrumentIdentificationCode = Encoding.ASCII.GetString(data.Slice(offset, 12)).TrimEnd();
            offset += 12;
            msg.InstrumentIdentificationCodeType = data[offset++];
            msg.Currency = Encoding.ASCII.GetString(data.Slice(offset, 3)).TrimEnd();
            offset += 3;
            msg.VenueOfExecution = Encoding.ASCII.GetString(data.Slice(offset, 4)).TrimEnd();
            offset += 4;
            msg.PriceNotation = data[offset++];
            ulong notionalAmountRaw = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            msg.NotionalAmount = (notionalAmountRaw >> 63) != 0 ? -(long)(notionalAmountRaw & 0x7FFFFFFFFFFFFFFFUL) : (long)notionalAmountRaw;
            offset += 8;
            msg.Reserved4 = Encoding.ASCII.GetString(data.Slice(offset, 3)).TrimEnd();
            offset += 3;
            msg.PublicationDateTime = BinaryPrimitives.ReadUInt64LittleEndian(data.Slice(offset, 8));
            offset += 8;
            msg.BuyerFirm = Encoding.ASCII.GetString(data.Slice(offset, 6)).TrimEnd();
            offset += 6;
            msg.SellerFirm = Encoding.ASCII.GetString(data.Slice(offset, 6)).TrimEnd();
            offset += 6;
            msg.CancellationFlag = Encoding.ASCII.GetString(data.Slice(offset, 4)).TrimEnd();
            offset += 4;
            msg.AmendmentFlag = Encoding.ASCII.GetString(data.Slice(offset, 4)).TrimEnd();
            offset += 4;
            msg.SubBook = data[offset++];
            msg.Flags = data[offset++];
            msg.AuctionType = data[offset++];
        }

        public static void Encode(MitchRecoveryTrade msg, Span<byte> data)
        {
            int offset = 0;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.Nanosecond);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.OrderId);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.ExecutedQuantity);
            offset += 4;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.Price < 0 ? (ulong)(-msg.Price) | 0x8000000000000000UL : (ulong)msg.Price);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.InstrumentId);
            offset += 4;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.TradeId);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.Reserved1 < 0 ? (ulong)(-msg.Reserved1) | 0x8000000000000000UL : (ulong)msg.Reserved1);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.Reserved2 < 0 ? (ulong)(-msg.Reserved2) | 0x8000000000000000UL : (ulong)msg.Reserved2);
            offset += 8;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.Reserved3 < 0 ? (ulong)(-msg.Reserved3) | 0x8000000000000000UL : (ulong)msg.Reserved3);
            offset += 8;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.TradingDateTime);
            offset += 8;
            var instrumentIdentificationCodeBytes = Encoding.ASCII.GetBytes((msg.InstrumentIdentificationCode ?? string.Empty).PadRight(12));
            instrumentIdentificationCodeBytes.AsSpan(0, 12).CopyTo(data.Slice(offset, 12));
            offset += 12;
            data[offset++] = (byte)msg.InstrumentIdentificationCodeType;
            var currencyBytes = Encoding.ASCII.GetBytes((msg.Currency ?? string.Empty).PadRight(3));
            currencyBytes.AsSpan(0, 3).CopyTo(data.Slice(offset, 3));
            offset += 3;
            var venueOfExecutionBytes = Encoding.ASCII.GetBytes((msg.VenueOfExecution ?? string.Empty).PadRight(4));
            venueOfExecutionBytes.AsSpan(0, 4).CopyTo(data.Slice(offset, 4));
            offset += 4;
            data[offset++] = (byte)msg.PriceNotation;
            BinaryPrimitives.WriteUInt64LittleEndian(data.Slice(offset, 8), msg.NotionalAmount < 0 ? (ulong)(-msg.NotionalAmount) | 0x8000000000000000UL : (ulong)msg.NotionalAmount);
            offset += 8;
            var reserved4Bytes = Encoding.ASCII.GetBytes((msg.Reserved4 ?? string.Empty).PadRight(3));
            reserved4Bytes.AsSpan(0, 3).CopyTo(data.Slice(offset, 3));
            offset += 3;
            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.PublicationDateTime);
            offset += 8;
            var buyerFirmBytes = Encoding.ASCII.GetBytes((msg.BuyerFirm ?? string.Empty).PadRight(6));
            buyerFirmBytes.AsSpan(0, 6).CopyTo(data.Slice(offset, 6));
            offset += 6;
            var sellerFirmBytes = Encoding.ASCII.GetBytes((msg.SellerFirm ?? string.Empty).PadRight(6));
            sellerFirmBytes.AsSpan(0, 6).CopyTo(data.Slice(offset, 6));
            offset += 6;
            var cancellationFlagBytes = Encoding.ASCII.GetBytes((msg.CancellationFlag ?? string.Empty).PadRight(4));
            cancellationFlagBytes.AsSpan(0, 4).CopyTo(data.Slice(offset, 4));
            offset += 4;
            var amendmentFlagBytes = Encoding.ASCII.GetBytes((msg.AmendmentFlag ?? string.Empty).PadRight(4));
            amendmentFlagBytes.AsSpan(0, 4).CopyTo(data.Slice(offset, 4));
            offset += 4;
            data[offset++] = (byte)msg.SubBook;
            data[offset++] = (byte)msg.Flags;
            data[offset++] = (byte)msg.AuctionType;
        }
    }
}
