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
        public byte[] CompId { get; set; }

        /// <summary>Password assigned to the CompID</summary>
        public byte[] Password { get; set; }

        /// <summary>New password for CompID</summary>
        public byte[] NewPassword { get; set; }

        /// <summary>Protocol version (1-9)</summary>
        public int ProtocolVersion { get; set; }

        /// <summary>Original IP Address the user</summary>
        public byte[] IpAddress { get; set; }

        /// <summary>Nome da solução certificada</summary>
        public byte[] CertifiedSolution { get; set; }

        public static void Parse(NativeLogon msg, Span<byte> data)
        {
            msg.CompId = data.Slice(0, 11).ToArray();
            msg.Password = data.Slice(11, 25).ToArray();
            msg.NewPassword = data.Slice(36, 25).ToArray();
            msg.ProtocolVersion = BitConverter.ToInt32(data.Slice(61, 4));
            msg.IpAddress = data.Slice(65, 15).ToArray();
            msg.CertifiedSolution = data.Slice(80, 44).ToArray();
        }

        public static void Encode(NativeLogon msg, Span<byte> data)
        {
            msg.CompId.CopyTo(data.Slice(0, 11));
            msg.Password.CopyTo(data.Slice(11, 25));
            msg.NewPassword.CopyTo(data.Slice(36, 25));
            BitConverter.TryWriteBytes(data.Slice(61, 4), msg.ProtocolVersion);
            msg.IpAddress.CopyTo(data.Slice(65, 15));
            msg.CertifiedSolution.CopyTo(data.Slice(80, 44));
        }
    }
}
