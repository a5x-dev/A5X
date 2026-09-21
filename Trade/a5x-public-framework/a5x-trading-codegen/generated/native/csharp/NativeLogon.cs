using System;
using System.Text;

namespace A5X.Trading.Native.Model
{
    /// <summary>
    /// Logon request
    /// Message Type: A
    /// </summary>
    public class NativeLogon
    {
        /// <summary>CompID assigned to the client</summary>
        public string CompId { get; set; }

        /// <summary>Password assigned to the CompID</summary>
        public string Password { get; set; }

        /// <summary>New password for CompID</summary>
        public string NewPassword { get; set; }

        /// <summary>Specifies the version of protocol. The system will only accept values from 1-9. If left empty, the system will consider the default value defined in system</summary>
        public int ProtocolVersion { get; set; }

        /// <summary>Original IP Address the user</summary>
        public string IpAddress { get; set; }

        /// <summary>Nome da solução certificada</summary>
        public string CertifiedSolution { get; set; }

        public static void Parse(NativeLogon msg, Span<byte> data)
        {
            msg.CompId = Encoding.ASCII.GetString(data.Slice(0, 11)).TrimEnd('\0');
            msg.Password = Encoding.ASCII.GetString(data.Slice(11, 25)).TrimEnd('\0');
            msg.NewPassword = Encoding.ASCII.GetString(data.Slice(36, 25)).TrimEnd('\0');
            msg.ProtocolVersion = BitConverter.ToInt32(data.Slice(61, 4));
            msg.IpAddress = Encoding.ASCII.GetString(data.Slice(65, 15)).TrimEnd('\0');
            msg.CertifiedSolution = Encoding.ASCII.GetString(data.Slice(80, 44)).TrimEnd('\0');
        }

        public static void Encode(NativeLogon msg, Span<byte> data)
        {
            Encoding.ASCII.GetBytes((msg.CompId ?? string.Empty).PadRight(11, '\0')).AsSpan(0, 11).CopyTo(data.Slice(0, 11));
            Encoding.ASCII.GetBytes((msg.Password ?? string.Empty).PadRight(25, '\0')).AsSpan(0, 25).CopyTo(data.Slice(11, 25));
            Encoding.ASCII.GetBytes((msg.NewPassword ?? string.Empty).PadRight(25, '\0')).AsSpan(0, 25).CopyTo(data.Slice(36, 25));
            BitConverter.TryWriteBytes(data.Slice(61, 4), msg.ProtocolVersion);
            Encoding.ASCII.GetBytes((msg.IpAddress ?? string.Empty).PadRight(15, '\0')).AsSpan(0, 15).CopyTo(data.Slice(65, 15));
            Encoding.ASCII.GetBytes((msg.CertifiedSolution ?? string.Empty).PadRight(44, '\0')).AsSpan(0, 44).CopyTo(data.Slice(80, 44));
        }
    }
}
